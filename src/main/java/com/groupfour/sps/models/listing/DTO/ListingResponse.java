package com.groupfour.sps.models.listing.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ListingResponse {
    private String id;
    private String title;
    private String description;
    private Double price;
    private String sellerId;
    private LocalDate datePosted;
}
