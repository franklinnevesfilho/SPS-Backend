package com.groupfour.snb.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name = "listing_table")
public class Listing {

    // Listing contains userId within the database.
    // We will be using a many-to-one relationship
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID listingId;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private LocalDate datePosted;

    private LocalDate datePurchased;

    @NonNull
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false)
    private User user;

    public Listing(){
        datePosted = LocalDate.now();
    }
}

