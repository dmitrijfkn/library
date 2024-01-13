package com.pet.library.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ErrorService {
    public ResponseEntity generateBadRequestResponse(String errorMessage) {
        return generateBadRequestResponse(Collections.singletonList(errorMessage));
    }

    public ResponseEntity generateBadRequestResponse(List<String> errorMessages) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode errorNode = mapper.createObjectNode();

        errorNode.put("code", "400");
        errorNode.put("message", "Bad Request");

        ArrayNode errors = mapper.createArrayNode();
        for (String errorMessage : errorMessages) {
            errors.add(errorMessage);
        }

        errorNode.set("details", errors);

        return ResponseEntity.badRequest().body(errorNode.toPrettyString());
    }

    public ResponseEntity generateBadRequestResponse(BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();

        for (ObjectError error : bindingResult.getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }

        return generateBadRequestResponse(errors);
    }
}
