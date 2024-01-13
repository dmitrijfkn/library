package com.pet.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_authors")
@Setter
@Getter
public class ItemAuthor {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    private Integer authorOrder;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnore
    @NotNull
    LibraryItem libraryItem;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @NotNull
    Author author;
}
