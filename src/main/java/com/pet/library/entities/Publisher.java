package com.pet.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "publishers")
@Setter
@Getter
public class Publisher {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotEmpty
    private String publisherInfo;

    @OneToMany(mappedBy = "publisher")
    @JsonIgnore
    private List<LibraryItem> libraryItems;
}
