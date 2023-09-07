package com.groupfour.snb.services;

import com.groupfour.snb.models.Listing;
import com.groupfour.snb.repositories.ListingRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class ListingService {
    private ListingRepository listingRepository;
    private UserService userService;

    public Optional<Listing> getListingsById(UUID listingId) {
        return listingRepository.findById(listingId);
    }

    public Iterable<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public void addListing(Listing listing, UUID id) {
        listingRepository.save(listing);
    }
}
