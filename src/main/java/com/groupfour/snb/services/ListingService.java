package com.groupfour.snb.services;

import com.groupfour.snb.models.Listing;
import com.groupfour.snb.repositories.ListingRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final UserService userService;

    public Optional<Listing> getListingsById(UUID listingId) {
        return listingRepository.findById(listingId);
    }

    public Iterable<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public void addListing(Listing listing) {

        listingRepository.save(listing);
    }
}
