package com.groupfour.snb.models.user;

import com.groupfour.snb.models.listing.Listing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class VerifiedSeller {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(name = "license_number")
    private String license;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "seller")
    private List<Listing> postedListings = new LinkedList<>();


}
