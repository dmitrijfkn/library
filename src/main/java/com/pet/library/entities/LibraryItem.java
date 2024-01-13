package com.pet.library.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "library_items")
@Setter
@Getter
public class LibraryItem {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotEmpty
    @Size(min = 1, max = 255)
    private String title;

    @Column
    @NotNull
    private Integer yearOfPublication;

    @Column
    @NotEmpty
    @Size(min = 1, max = 255)
    private String bibliographicIndex;

    @Column
    private Instant added;

    @Column
    @NotNull
    @Min(0)
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Publisher publisher;

    @OneToMany(mappedBy = "libraryItem", cascade = CascadeType.ALL)
    private Set<ItemAuthor> authors;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "item_category",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private ItemType type;

    // TODO Maybe add keywords for search or Book library kode
}