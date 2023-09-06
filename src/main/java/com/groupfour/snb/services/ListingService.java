package com.groupfour.snb.services;

import com.groupfour.snb.models.Listing;
import com.groupfour.snb.repositories.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ListingService {
    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private UserService userService;

    public Optional<Listing> getListingsById(UUID listingId) {
        return listingRepository.findById(listingId);
    }

    public Iterable<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public void addListing(Listing listing, UUID id) {
        userService.getUserByUUID(id).ifPresent(listing::setUser);
        listingRepository.save(listing);
    }
}
