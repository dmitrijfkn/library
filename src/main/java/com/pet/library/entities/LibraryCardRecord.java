package com.pet.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "library_card_records")
@Setter
@Getter
public class LibraryCardRecord {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Instant issuedTime;

    @Column
    private Instant returnedTime;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne(cascade=CascadeType.ALL)
    private LibraryItem libraryItem;

    @ManyToOne
    @JoinColumn(name = "librarian_issued_id")
    private User librarianIssued;


    @ManyToOne
    @JoinColumn(name = "librarian_receiver_id")
    private User librarianReceiver;
}
