package com.groupfour.snb.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * <h1>Listing Model</h1>
 * This model is structured around listings that user will be able to sell on our applicaiton.
 * It will automatically generate a posted date once teh object is created,
 * and will generate the purchased date once purchased.
 *
 * @author Franklin Neves Filho
 * @Last-Modified: 09/08/2023
 */
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "listings")
public class Listing {

    // Listing contains userId within the database.
    // We will be using a many-to-one relationship to map them
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID listingId;

    @NonNull
    private String name;

    @NonNull
    private String description;

    // The date posted will be automatically created once the listing is created
    private LocalDate datePosted = LocalDate.now();
    private LocalDate datePurchased;

    // Will update the date purchased and return the item
    public Listing purchased() {
        this.datePurchased = LocalDate.now();
        return this;
    }
}

