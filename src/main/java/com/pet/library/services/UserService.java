package com.pet.library.services;


import com.pet.library.dtos.UserRegistrationDTO;
import com.pet.library.dtos.UserRequest;
import com.pet.library.dtos.UserResponse;

import java.util.List;


public interface UserService {

    UserResponse saveNewUser(UserRegistrationDTO userRequest);

    UserResponse getUser();

    List<UserResponse> getAllUser();

    UserResponse saveNewLibrarian(UserRegistrationDTO userRequest);
}
