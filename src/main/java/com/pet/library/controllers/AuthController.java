package com.pet.library.controllers;


import com.pet.library.dtos.AuthRequestDTO;
import com.pet.library.dtos.JwtResponseDTO;
import com.pet.library.dtos.RefreshTokenRequestDTO;
import com.pet.library.entities.RefreshToken;
import com.pet.library.error.ApplicationError;
import com.pet.library.services.JwtService;
import com.pet.library.services.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.cookieExpiry}")
    private int cookieExpiry;


    /**
     * Authenticates a user by validating the provided username and password.
     * If the authentication is successful, generates an access token and a refresh token,
     * associates the access token with a cookie, saves generated token
     * and returns a {@link JwtResponseDTO} containing the access and refresh tokens.
     *
     * @param authRequestDTO
     *                  - Object with username and password for authentication
     * @param response
     *                  - Object of {@link jakarta.servlet.http.HttpServletResponse} class, used for further use of cookies
     *
     * @return JwtResponseDTO
     *                  - Object which should contain access and refresh tokens
     *
     * @throws UsernameNotFoundException
     *                  - If the provided user credentials are invalid.
     */
    @Operation(summary = "Login to the system using username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in successfully.",
                    content = @Content(schema = @Schema(implementation = JwtResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Invalid user credentials.",
                    content = @Content(schema = @Schema(implementation = ApplicationError.class)))
    })
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));

        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            String accessToken = jwtService.GenerateToken(authRequestDTO.getUsername());
            // set accessToken to cookie header
            ResponseCookie cookie = ResponseCookie
                    .from("accessToken", accessToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(cookieExpiry)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return JwtResponseDTO.builder()
                    .accessToken(accessToken)
                    .token(refreshToken.getToken()).build();

        } else {
            throw new UsernameNotFoundException("Invalid user credentials.");
        }

    }


    /**
     * Refreshes a JWT token using a valid refresh token. The provided refresh token is validated,
     * and if it is valid, a new access token is generated for the associated user.
     *
     * @param refreshTokenRequestDTO
     *                  - Object containing the refresh token to be used for token refresh.
     *
     * @return JwtResponseDTO
     *                  - Object containing the refreshed access token along with the original refresh token.
     *
     * @throws UsernameNotFoundException
     *                  - If the provided refresh token is invalid.
     */
    @Operation(summary = "Refresh JWT token using refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully.",
                    content = @Content(schema = @Schema(implementation = JwtResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Refresh token is invalid.",
                    content = @Content(schema = @Schema(implementation = ApplicationError.class)))
    })
    @PostMapping("/refreshToken")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();

                }).orElseThrow(() -> new UsernameNotFoundException("Refresh token is invalid"));
    }

}