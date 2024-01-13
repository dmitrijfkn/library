package com.pet.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "authors")
@Setter
@Getter
public class Author {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    private String fullName;

    @Column
    private String pseudonym;

    @OneToMany(mappedBy = "author")
    @OrderBy("authorOrder")
    @JsonIgnore
    Set<ItemAuthor> items;
}
