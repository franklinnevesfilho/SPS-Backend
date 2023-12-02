package com.groupfour.sps.services;

import com.groupfour.sps.models.Picture;
import com.groupfour.sps.models.listing.DTO.CreateListing;
import com.groupfour.sps.models.listing.DTO.ListingResponse;
import com.groupfour.sps.models.listing.Listing;
import com.groupfour.sps.models.listing.Question;
import com.groupfour.sps.models.user.User;
import com.groupfour.sps.repositories.PictureRepository;
import com.groupfour.sps.repositories.listing.ListingRepository;
import com.groupfour.sps.repositories.listing.MessageRepository;
import com.groupfour.sps.models.responses.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Responsible for all operations involving listings
 *
 * @author Franklin Neves
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ListingService extends MainService {
    private final ListingRepository listingRepository;
    private final MessageRepository messageRepository;
    private final PictureRepository pictureRepository;

    public Response addListing(CreateListing listing, String userId) {
        Listing listingCreated = listingRepository.save(Listing.builder()
                .seller(User.builder().id(userId).build())
                .title(listing.title())
                .description(listing.description())
                .price(listing.price())
                .build());
        return Response.builder()
                .node(mapToJson(listingCreated))
                .build();
    }
    public Response getListingWithId(String id){
        List<String> errors = new LinkedList<>();
        Optional<Listing> foundListing = listingRepository.findById(id);
        ListingResponse listing = ListingResponse.builder().build();
        if(foundListing.isPresent()){
            Listing found = foundListing.get();
            listing = ListingResponse.builder()
                    .id(found.getId())
                    .datePosted(found.getDatePosted())
                    .description(found.getDescription())
                    .title(found.getTitle())
                    .price(found.getPrice())
                    .sellerId(found.getSeller().getId())
                    .build();
            log.info("SellerId : " + found.getSeller().getId() );
        }
        return Response.builder()
                .node(mapToJson(listing))
                .errors(errors)
                .build();
    }
    public void addPicture(String listingId , Picture picture){
        pictureRepository.save(picture);
    }
    public Response addMessage(User user, String listingId, String message) {
        List<String> errors = new LinkedList<>();
        Optional<Listing> listing = listingRepository.findListingById(listingId);
        if(listing.isPresent()){
            Listing foundListing = listing.get();
            messageRepository.save(
                    Question.builder()
                            .user(user)
                            .listing(foundListing)
                            .question(message)
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

        // Remove all purchased listings
        listings.removeIf(listing -> listing.getDatePurchased() != null);
        List<ListingResponse> response = new LinkedList<>();
        // create response
        listings.forEach(listing -> {
            User seller = listing.getSeller();
            response.add(ListingResponse.builder()
                    .id(listing.getId())
                    .sellerId(seller.getId())
                    .title(listing.getTitle())
                    .description(listing.getDescription())
                    .price(listing.getPrice())
                    .build());
        });

        return Response.builder()
                .node(mapToJson(response))
                .build();
    }
}
