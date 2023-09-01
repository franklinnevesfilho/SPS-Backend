package com.groupfour.snb.models.listing;

import java.util.Date;

public class Listing {

    // Listing contains userId within the database.
    // We will be using a many-to-one relationship
    private int listingId;

    private Date datePosted;
    private Date datePurchased;

    private String name;
    private String description;

    // Is type int as a placeholder since this will be stored in database
    private int image;
}
