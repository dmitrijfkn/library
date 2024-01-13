package com.pet.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Column(unique = true)
    private String email;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(length = 12, unique = true)
    private String phoneNumber;

    @Column
    private Instant registrationTime;

    @OneToMany(mappedBy = "user")
    private Set<LibraryCardRecord> records;

    @OneToMany(mappedBy = "user")
    private Set<RefreshToken> refreshTokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
