package com.pet.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "item_types")
@Setter
@Getter
public class ItemType {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String type;

    @OneToMany(mappedBy = "type")
    @JsonIgnore
    private Set<LibraryItem> libraryItems;
}
