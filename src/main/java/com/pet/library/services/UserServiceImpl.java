package com.pet.library.services;

import com.pet.library.dtos.UserRegistrationDTO;
import com.pet.library.dtos.UserResponse;
import com.pet.library.entities.Role;
import com.pet.library.entities.User;
import com.pet.library.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    ModelMapper modelMapper = new ModelMapper();
    private final RoleService roleService;

    public UserServiceImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public UserResponse saveNewUser(UserRegistrationDTO userRequest) {
        return saveNewUser(userRequest, null);
    }

    @Override
    public UserResponse saveNewLibrarian(UserRegistrationDTO userRequest) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.librarian);

        return saveNewUser(userRequest, roles);
    }

    public UserResponse saveNewUser(UserRegistrationDTO userRequest, Set<Role> roles) {

        User user = modelMapper.map(userRequest, User.class);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRegistrationTime(Instant.now());

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new IllegalArgumentException("User with phoneNumber " + user.getPhoneNumber() + " already exists");
        } else {
            if (roles != null && !roles.isEmpty()) {
                user.setRoles(roles);
            }
            userRepository.save(user);
        }

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String usernameFromAccessToken = userDetail.getUsername();
        User user = userRepository.findByEmail(usernameFromAccessToken);
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        Type setOfDTOsType = new TypeToken<List<UserResponse>>() {
        }.getType();
        return modelMapper.map(users, setOfDTOsType);
    }


}
