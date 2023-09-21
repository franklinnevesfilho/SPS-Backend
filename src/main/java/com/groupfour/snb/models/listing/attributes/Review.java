package com.groupfour.snb.models.listing.attributes;

import com.groupfour.snb.models.STARS;
import lombok.Data;

@Data
public class Review {
    private STARS stars;
    private String message;
}
