package com.pet.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "roles")
@Setter
@Getter
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String authority;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;

    @Override
    public String getAuthority() {
        return authority;
    }
}
