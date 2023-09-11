package com.groupfour.snb.services;

import com.groupfour.snb.models.listing.Image;
import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.models.listing.Message;
import com.groupfour.snb.repositories.listing.ImageRepository;
import com.groupfour.snb.repositories.listing.ListingRepository;
import com.groupfour.snb.repositories.listing.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final ImageRepository imageRepository;
    private final MessageRepository messageRepository;
    public Listing addListing(Listing listing) {
        return listingRepository.save(listing);
    }
    public void addImages(String listingId , Set<Image> images){
        images.forEach(image -> {
            image.setListing(listingRepository.findById(listingId).orElseThrow());
        });
        imageRepository.saveAll(images);
    }
    public void addMessage(String listingId, Message message) {
        Listing listing = listingRepository.findListingById(listingId);
        message.setListing(listing);
        messageRepository.save(message);
    }
}
