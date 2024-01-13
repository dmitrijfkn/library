package com.pet.library.services;

import com.pet.library.entities.Role;
import com.pet.library.entities.User;
import com.pet.library.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    Role admin;
    Role librarian;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.admin = roleRepository.findByAuthority("ROLE_ADMIN");
        this.librarian = roleRepository.findByAuthority("ROLE_LIBRARIAN");
    }



    public boolean isLibrarianOrAdmin(User user) {

        return user.getAuthorities().contains(admin)
                || user.getAuthorities().contains(librarian);

    }
}
