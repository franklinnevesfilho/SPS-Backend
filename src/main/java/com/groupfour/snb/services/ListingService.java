package com.groupfour.snb.services;

import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.repositories.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class ListingService {

    final private Listing[] listings ={
            new Listing("Name", "Description", LocalDate.of(2023, 9,1)),
            new Listing("Name1", "Description1", LocalDate.of(2023, Calendar.SEPTEMBER,2)),
            new Listing("Name2", "Description2", LocalDate.of(2023, Calendar.SEPTEMBER,3)),
            new Listing("Name3", "Description3", LocalDate.of(2023, Calendar.SEPTEMBER,4))
    };

    @Autowired
    private ListingRepository listingRepository;

    public void addListings() {
        listingRepository.saveAll(Arrays.stream(listings).toList());
    }

    public Optional<Listing> getListingsById(UUID listingId) {
        return listingRepository.findById(listingId);
    }

    public Iterable<Listing> getAllListings() {
        return listingRepository.findAll();
    }
}
