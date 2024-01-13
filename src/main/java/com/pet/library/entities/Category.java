package com.pet.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "categories")
@Setter
@Getter
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(mappedBy = "categories", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    Set<LibraryItem> libraryItems;

    @Column
    @NotNull
    String categoryName;
}
