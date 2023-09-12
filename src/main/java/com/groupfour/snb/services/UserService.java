package com.groupfour.snb.services;
import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.models.listing.ListingCreationDTO;
import com.groupfour.snb.models.user.SecurityUser;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ListingService listingService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return SecurityUser.builder().user(userRepository.findByEmail(username).orElseThrow()).build();
    }

    public User add(User user){
        return userRepository.save(user);
    }

    public User getById(String id){
        return userRepository.findById(id).orElseThrow();
    }

    public Iterable<User> getAll() {
         return userRepository.findAll();
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email: " + email +" not found"));
    }

    public Listing addListing(String userId, ListingCreationDTO listing) {
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
