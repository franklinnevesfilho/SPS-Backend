package com.groupfour.sps.services;

import com.groupfour.sps.models.listing.DTO.CreateListing;
import com.groupfour.sps.models.listing.attributes.Image;
import com.groupfour.sps.models.listing.Listing;
import com.groupfour.sps.models.listing.attributes.Message;
import com.groupfour.sps.models.user.User;
import com.groupfour.sps.repositories.listing.ImageRepository;
import com.groupfour.sps.repositories.listing.ListingRepository;
import com.groupfour.sps.repositories.listing.MessageRepository;
import com.groupfour.sps.utils.responses.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ListingService extends MainService {
    private final ListingRepository listingRepository;
    private final ImageRepository imageRepository;
    private final MessageRepository messageRepository;

    public Response addListing(CreateListing listing, String userId) {
        Listing listingCreated = listingRepository.save(Listing.builder()
                .seller(User.builder().id(userId).build())
                .title(listing.title())
                .description(listing.description())
                .build());
        return Response.builder()
                .node(mapToJson(listingCreated))
                .build();
    }
    public Response getListingWithId(String id){
        List<String> errors = new LinkedList<>();
        Optional<Listing> foundListing = listingRepository.findById(id);
        Listing listing = Listing.builder().build();
        if(foundListing.isPresent()){
            listing = foundListing.get();
        }
        return Response.builder()
                .node(mapToJson(listing))
                .errors(errors)
                .build();
    }
    public void addImages(String listingId , Set<Image> images){
        images.forEach(image -> image.setListing(listingRepository.findById(listingId).orElseThrow()));
        imageRepository.saveAll(images);
    }
    public Response addMessage(User user, String listingId, String message) {
        List<String> errors = new LinkedList<>();
        Optional<Listing> listing = listingRepository.findListingById(listingId);
        if(listing.isPresent()){
            Listing foundListing = listing.get();
            messageRepository.save(
                    Message.builder()
                            .user(user)
                            .listing(foundListing)
                            .message(message)
                            .build()
            );
        }else{
            errors.add("Listing with id: "+ listingId + " not found");
        }
        return Response.builder().node(mapToJson(message)).errors(errors).build();
    }
    public Response getAllListingsWithUser(String sellerId){
        return Response.builder()
                .node(mapToJson( listingRepository.findListingsBySellerId(sellerId)))
                .build();
    }

    public Response getAllListings(){
        List<Listing> listings = listingRepository.findAll();
        return Response.builder()
                .node(mapToJson(listings))
                .build();
    }
}
