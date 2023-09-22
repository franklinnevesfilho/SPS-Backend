package com.groupfour.snb.models.listing.attributes;

import com.groupfour.snb.models.listing.Listing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String type;
    @ManyToOne
    @JoinColumn(name = "listing_id")
    private Listing listing;

    private byte[] picByte;
}
