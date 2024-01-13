package com.pet.library.controllers;

import com.pet.library.dtos.UserRegistrationDTO;
import com.pet.library.dtos.UserResponse;
import com.pet.library.services.ErrorService;
import com.pet.library.services.JwtService;
import com.pet.library.services.RefreshTokenService;
import com.pet.library.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO Придумать нормальный маппинг
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ErrorService errorService;

    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity saveUser(@RequestBody @Valid UserRegistrationDTO userRequest,
                                   BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()){
                return errorService.generateBadRequestResponse(bindingResult);
            }

            UserResponse userResponse = userService.saveNewUser(userRequest);

            return ResponseEntity.ok(userResponse);
        }catch (IllegalArgumentException ilae){
            return errorService.generateBadRequestResponse(ilae.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("");
        }
    }

 // TODO Проверить что доступ только у админа
    @PostMapping(value = "/saveLibrarian")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity saveLibrarian(@RequestBody @Valid UserRegistrationDTO userRequest,
                                   BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()){
                return errorService.generateBadRequestResponse(bindingResult);
            }

            UserResponse userResponse = userService.saveNewLibrarian(userRequest);

            return ResponseEntity.ok(userResponse);
        }catch (IllegalArgumentException ilae){
            return errorService.generateBadRequestResponse(ilae.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("");
        }
    }



    // TODO Должна возвращать акк очень красиво, со всей информацией о книжках,
    //  сделать доступ только админу
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAllUsers() {
        try {
            List<UserResponse> userResponses = userService.getAllUser();
            return ResponseEntity.ok(userResponses);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // TODO Должна возвращать акк очень красиво, со всей информацией о книжках,
    //  сделать доступ только админу и самому пользователю
    @PostMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getUserProfile() {
        try {
            UserResponse userResponse = userService.getUser();
            return ResponseEntity.ok().body(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
