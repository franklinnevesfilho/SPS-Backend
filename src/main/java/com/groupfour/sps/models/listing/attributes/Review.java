package com.groupfour.sps.models.listing.attributes;

import lombok.Data;

@Data
public class Review {
    private STARS stars;
    private String message;
}
