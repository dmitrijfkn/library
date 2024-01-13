package com.pet.library.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    private Instant expiryDate;

    @ManyToOne
    private User user;
}
