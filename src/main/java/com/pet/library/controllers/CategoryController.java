package com.pet.library.controllers;

import com.pet.library.entities.Author;
import com.pet.library.entities.Category;
import com.pet.library.entities.Publisher;
import com.pet.library.error.ApplicationError;
import com.pet.library.services.CategoryService;
import com.pet.library.services.ErrorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ErrorService errorService;

    @Autowired
    public CategoryController(CategoryService categoryService, ErrorService errorService) {
        this.categoryService = categoryService;
        this.errorService = errorService;
    }


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<Category>> viewAllCategories() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }


    @Operation(summary = "Save new author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author saved successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Author.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationError.class))})
    })
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity saveNewCategory(@RequestBody @Valid Category category,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()  ) {
            return errorService.generateBadRequestResponse(bindingResult);
        }if(category.getId() != null){
            return errorService.generateBadRequestResponse("Incorrect use of save method with id defined");
        }

        return ResponseEntity.ok(categoryService.save(category));
    }


    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity updateCategory(@RequestBody @Valid Category category,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()  ) {
            return errorService.generateBadRequestResponse(bindingResult);
        }if(category.getId() == null){
            return errorService.generateBadRequestResponse("Incorrect use of update method without id defined");
        }

        return ResponseEntity.ok(categoryService.save(category));
    }
}