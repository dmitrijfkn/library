package com.pet.library.controllers;

import com.pet.library.entities.ItemType;
import com.pet.library.services.ErrorService;
import com.pet.library.services.ItemTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
public class ItemTypeController {
    private final ItemTypeService itemTypeService;
    private final ErrorService errorService;

    @Autowired
    ItemTypeController(ItemTypeService itemTypeService, ErrorService errorService) {
        this.itemTypeService = itemTypeService;
        this.errorService = errorService;
    }


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<ItemType>> viewAllItemTypes() {
        return new ResponseEntity<>(itemTypeService.findAll(), HttpStatus.OK);
    }


    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity saveNewItemType(@RequestBody @Valid ItemType itemType,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return errorService.generateBadRequestResponse(bindingResult);
        }
        if (itemType.getId() != null) {
            return errorService.generateBadRequestResponse("Incorrect use of save method with id defined");
        }

        return ResponseEntity.ok(itemTypeService.save(itemType));
    }


    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity updateItem(@RequestBody @Valid ItemType itemType,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return errorService.generateBadRequestResponse(bindingResult);
        }
        if (itemType.getId() == null) {
            return errorService.generateBadRequestResponse("Incorrect use of update method without id defined");
        }

        return ResponseEntity.ok(itemTypeService.save(itemType));
    }
}