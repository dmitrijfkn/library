package com.pet.library.controllers;

import com.pet.library.entities.Author;
import com.pet.library.error.ApplicationError;
import com.pet.library.services.AuthorService;
import com.pet.library.services.ErrorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final ErrorService errorService;

    public AuthorController(AuthorService authorService, ErrorService errorService) {
        this.authorService = authorService;
        this.errorService = errorService;
    }


    @Operation(summary = "Get all authors")
    @ApiResponse(responseCode = "200", description = "Authors returned successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Author.class))})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    List<Author> viewAllAuthors() {
        return authorService.findAll();
    }

    //TODO remove all entities from request params https://stackoverflow.com/questions/69639251/should-entity-class-be-used-as-request-body

    // TODO search for entities in response

    // TODO add @ResponseStatus to requests

    // TODO rewrite exception handling: https://habr.com/ru/articles/675716/

    //TODO rewrite Api docs:

    @Operation(summary = "Save new author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author saved successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Author.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationError.class))})
    })
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    Author saveNewAuthor(@RequestBody @Valid Author author,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getAllErrors().toString());
            //   errorService.generateBadRequestResponse(bindingResult);
        }
        if (author.getId() != null) {
            //    return errorService.generateBadRequestResponse("Incorrect use of save method with id defined");
        }

        return authorService.save(author);
    }


    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> updateAuthor(@RequestBody Author author,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return errorService.generateBadRequestResponse(bindingResult);
        }
        if (author.getId() == null) {
            return errorService.generateBadRequestResponse("Incorrect use of update method without id defined");
        }

        return ResponseEntity.ok(authorService.save(author));
    }

}
