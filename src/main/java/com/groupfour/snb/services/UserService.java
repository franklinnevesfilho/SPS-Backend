package com.groupfour.snb.services;
import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.models.listing.CreateListing;
import com.groupfour.snb.models.user.SecurityUser;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.repositories.UserRepository;
import com.groupfour.snb.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>User Service</h1>
 * This Service will perform actions by the User
 * @author Franklin Neves Filho
 */
@RequiredArgsConstructor
@Service
public class UserService extends MainService implements UserDetailsService{
    private final UserRepository userRepository;
    private final ListingService listingService;

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return SecurityUser.builder().user(userRepository.findByEmail(username).orElseThrow()).build();
    }

    public Response add(User user){
        userRepository.save(user);
        return Response.builder()
                .node(mapToJson(user))
                .build();
    }

    public Response getById(String id){
        List<String> errors = new LinkedList<>();
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            errors.add("User was not found");
        }
        return Response.builder()
                .node(mapToJson(user))
                .errors(errors)
                .build();
    }

    public Iterable<User> getAll() {

        return userRepository.findAll();
    }

    public User getByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email: " + email +" not found"));
    }

    public Listing getListing(String listingId){

        return listingService.getListingWithId(listingId);
    }

    public Listing addListing(String userId, CreateListing listing) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User with id: " + userId + " not found"));
        return listingService.addListing(Listing.builder().user(user).title(listing.getTitle()).description(listing.getDescription()).build());
    }

    public void buyListing(String userId, String listingId){
    User user = userRepository.findById(userId).orElseThrow();
    listingService.purchaseListing(user, listingId);
    }

    public void postMessage(String userId, String listingId, String message) {
        userRepository.findById(userId).ifPresent(user ->{
            listingService.addMessage(user, listingId, message);
        });
    }
}
