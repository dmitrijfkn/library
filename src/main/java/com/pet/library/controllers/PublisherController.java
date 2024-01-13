package com.pet.library.controllers;

import com.pet.library.entities.LibraryItem;
import com.pet.library.entities.Publisher;
import com.pet.library.services.ErrorService;
import com.pet.library.services.ItemService;
import com.pet.library.services.PublisherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    private final PublisherService publisherService;
    private final ErrorService errorService;

    @Autowired
    PublisherController(PublisherService publisherService, ErrorService errorService) {
        this.publisherService = publisherService;
        this.errorService = errorService;
    }


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<Publisher>> viewAllPublishers() {
        return new ResponseEntity<>(publisherService.findAll(), HttpStatus.OK);
    }


    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity saveNewPublisher(@RequestBody @Valid Publisher publisher,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()  ) {
            return errorService.generateBadRequestResponse(bindingResult);
        }if(publisher.getId() != null){
            return errorService.generateBadRequestResponse("Incorrect use of save method with id defined");
        }

        return ResponseEntity.ok(publisherService.save(publisher));
    }


    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity updatePublisher(@RequestBody @Valid Publisher publisher,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()  ) {
            return errorService.generateBadRequestResponse(bindingResult);
        }if(publisher.getId() == null){
            return errorService.generateBadRequestResponse("Incorrect use of update method without id defined");
        }

        return ResponseEntity.ok(publisherService.save(publisher));
    }
}