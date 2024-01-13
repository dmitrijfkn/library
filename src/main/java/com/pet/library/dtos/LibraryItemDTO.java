package com.pet.library.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Setter
@Getter
public class LibraryItemDTO {

    private Long id;

    @NotEmpty
    @Size(min = 1, max = 255)
    private String title;


    @NotNull
    private Integer yearOfPublication;


    @NotEmpty
    @Size(min = 1, max = 255)
    private String bibliographicIndex;


    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    @Min(0)
    private Long publisherId;

    // Map of authors where first parameter - author id
    // second parameter - number in order
    private Map<Long, Integer> authorIds;

    @NotEmpty
    private Set<Long> categoryIds;


    @NotNull
    @Min(0)
    private Long typeId;
}