package com.pet.library.controllers;

import com.pet.library.dtos.LibraryItemDTO;
import com.pet.library.entities.LibraryItem;
import com.pet.library.error.ResourceNotFoundException;
import com.pet.library.repositories.ItemRepository;
import com.pet.library.services.ErrorService;
import com.pet.library.services.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final ErrorService errorService;

    @Autowired
    ItemController(ItemRepository itemRepository, ItemService itemService, ErrorService errorService) {
        this.itemRepository = itemRepository;
        this.itemService = itemService;
        this.errorService = errorService;
    }


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<LibraryItem>> viewAllItems() {
        return new ResponseEntity<>(itemRepository.findAll(), HttpStatus.OK);
    }


    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity saveNewItem(@RequestBody @Valid LibraryItemDTO libraryItemDTO,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return errorService.generateBadRequestResponse(bindingResult);
        }
        if (libraryItemDTO.getId() != null) {
            return errorService.generateBadRequestResponse("Incorrect use of save method with id defined");
        }
        itemService.save(libraryItemDTO);
        // TODO make LibraryItem to parse without an error

        // return ResponseEntity.ok(itemService.save(libraryItemDTO));

        return ResponseEntity.ok("OK");
    }

    // TODO remake update method
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity updateItem(@RequestBody @Valid LibraryItemDTO libraryItemDTO,
                              BindingResult bindingResult) {

        if (true) {
            throw new ResourceNotFoundException("Test exception will work every time");
        }

        if (bindingResult.hasErrors()) {
            return errorService.generateBadRequestResponse(bindingResult);
        }
        if (libraryItemDTO.getId() == null) {
            return errorService.generateBadRequestResponse("Incorrect use of update method without id defined");
        }

        // TODO make LibraryItem to parse without an error

        // return ResponseEntity.ok(itemService.save(libraryItemDTO));

        return ResponseEntity.ok("OK");
    }
}
