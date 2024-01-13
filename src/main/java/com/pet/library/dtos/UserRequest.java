package com.pet.library.dtos;


import com.pet.library.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {

    private Long id;
    private String email;
    private String password;
    private Set<Role> roles;

}
