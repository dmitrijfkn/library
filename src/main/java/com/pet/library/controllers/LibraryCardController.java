package com.pet.library.controllers;

import com.pet.library.entities.User;
import com.pet.library.repositories.LibraryCardRecordRepository;
import com.pet.library.services.ErrorService;
import com.pet.library.services.LibraryCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/libraryCard")
public class LibraryCardController {


    @Autowired
    public LibraryCardController(LibraryCardRecordRepository libraryCardRecordRepository, ErrorService errorService, LibraryCardService libraryCardService) {
        this.libraryCardRecordRepository = libraryCardRecordRepository;
        this.errorService = errorService;
        this.libraryCardService = libraryCardService;
    }


    private final LibraryCardRecordRepository libraryCardRecordRepository;

    private final ErrorService errorService;

    private final LibraryCardService libraryCardService;
/*
    @GetMapping("/{userId}")
    ResponseEntity ViewLibraryCard(@PathVariable Long userId) {

        LibraryCard libraryCard = libraryCardRecordRepository.findByUserId(userId);
        if (libraryCard == null) {
            errorService.generateBadRequestResponse("Library card with this Id isn't founded");
        }
        return ResponseEntity.ok(libraryCard);
    }
    */


    @PostMapping("/give")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity giveItemToUser(@RequestBody Long userId, @RequestBody Long itemId, Principal principal) {
        try {
            Long issuerId = ((User) principal).getId();
            return ResponseEntity.ok(
                    libraryCardService.giveItemToUser(userId, itemId, issuerId)
            );
        } catch (IllegalArgumentException iae) {
            return errorService.generateBadRequestResponse(iae.getMessage());
        }
    }


    @PostMapping("/return")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity returnItemFromUser(@RequestBody Long userId, @RequestBody Long recordId, Principal principal) {
        try {
            Long receiverId = ((User) principal).getId();
            return ResponseEntity.ok(
                    libraryCardService.returnItemFromUser(userId, recordId, receiverId)
            );
        } catch (IllegalArgumentException iae) {
            return errorService.generateBadRequestResponse(iae.getMessage());
        }
    }


}