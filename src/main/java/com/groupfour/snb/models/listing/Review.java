package com.groupfour.snb.models.listing;

import com.groupfour.snb.models.STARS;
import lombok.Data;

@Data
public class Review {
    private STARS stars;
    private String message;
}
