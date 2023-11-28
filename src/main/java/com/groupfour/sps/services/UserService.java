package com.groupfour.sps.services;

import com.groupfour.sps.models.Picture;
import com.groupfour.sps.models.PictureDTO;
import com.groupfour.sps.models.listing.DTO.ListingResponse;
import com.groupfour.sps.models.listing.Listing;
import com.groupfour.sps.models.user.DTO.SellerRequest;
import com.groupfour.sps.models.user.DTO.SellerResponse;
import com.groupfour.sps.models.user.Role;
import com.groupfour.sps.models.user.User;
import com.groupfour.sps.models.user.UserProfile;
import com.groupfour.sps.repositories.PictureRepository;
import com.groupfour.sps.repositories.UserRepository;
import com.groupfour.sps.repositories.listing.ListingRepository;
import com.groupfour.sps.utils.responses.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>User Service</h1>
 * This Service will perform actions by the User
 * @author Franklin Neves Filho
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService extends MainService  {
    private final ListingRepository listingRepository;
    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public void save(User user){
        userRepository.save(user);
        Response.builder()
                .node(mapToJson(user))
                .build();
    }

    public Response getAll() {
        return Response.builder()
                .node(mapToJson(userRepository.findAll()))
                .build();
    }

    public Response getByEmail(String email) {
        List<String> errors = new LinkedList<>();

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            errors.add("User was not found");
        }

        return Response.builder()
                .node(mapToJson(user))
                .errors(errors)
                .build();
    }
    public void enableUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
        Response.builder()
                .node(mapToJson(user))
                .build();
    }

    public Response sellerRequest(String userId, String licenseNum){
        List<String> errors = new LinkedList<>();
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = null;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            Role role = roleService.getRoleByAuthority("SELLER");
            user.getAuthorities().add(role);
            user.setLicense(licenseNum);
            userRepository.save(user);
        }else{
            errors.add("User not found");
        }

        return Response.builder().node(mapToJson(user)).errors(errors).build();
    }

    public Response addToCart(String listingId, String userId){
        Optional<Listing> listing = listingRepository.findById(listingId);
        Optional<User> user = userRepository.findById(userId);
        List<String> errors = new LinkedList<>();
        List<ListingResponse> cartResponse = new LinkedList<>();

        if(user.isPresent()){
            if(listing.isPresent()){
                List<Listing> cart = user.get().getCart();
                if(!cart.contains(listing.get())){
                    cart.add(listing.get());
                }
                userRepository.save(user.get());
            }else{
                errors.add("Listing with id: " + listingId + " - not found");
            }
            List<Listing> cart = user.get().getCart();
            cart.forEach((item) ->{
                cartResponse.add(ListingResponse.builder()
                                .id(item.getId())
                                .datePosted(item.getDatePosted())
                                .description(item.getDescription())
                                .title(item.getTitle())
                                .sellerId(item.getSeller().getId())
                                .price(item.getPrice())
                        .build());
            });
        }else{
            errors.add("user not found");
        }
        return Response.builder().node(mapToJson(cartResponse)).errors(errors).build();
    }

    public Response getSellerResponse(SellerRequest sellerId) {
        Optional<User> user = userRepository.findById(sellerId.getSellerId());
        List<String> errors = new LinkedList<>();
        SellerResponse seller = null;
        if(user.isPresent()){
            seller = SellerResponse.builder()
                    .firstName(user.get().getFirstName())
                    .lastName(user.get().getLastName())
                    .build();
        }else{
            errors.add("Seller not found");
        }

        return Response.builder()
                .node(mapToJson(seller))
                .errors(errors)
                .build();
    }

    public Response getUserCart(String userId) {
        Optional<User> user = userRepository.findById(userId);
        List<ListingResponse> cartResponse = new LinkedList<>();
        List<String> errors = new LinkedList<>();
        if(user.isPresent()){
            List<Listing> cart = user.get().getCart();
            cart.forEach(listing -> cartResponse.add(ListingResponse.builder()
                            .id(listing.getId())
                            .price(listing.getPrice())
                            .sellerId(listing.getSeller().getId())
                            .title(listing.getTitle())
                            .description(listing.getDescription())
                            .datePosted(listing.getDatePosted())
                    .build()));
        }else{
            errors.add("User not found");
        }

        return Response.builder().node(mapToJson(cartResponse)).errors(errors).build();
    }

    public Response removeFromCart(String listingId, String userId) {
        Optional<User> user = userRepository.findById(userId);
        List<String> errors = new LinkedList<>();
        List<ListingResponse> cartResponse = new LinkedList<>();

        if(user.isPresent()){
            List<Listing> cart = user.get().getCart();
            cart.removeIf((listing)-> listing.getId().equals(listingId));
            user.get().setCart(cart);
            userRepository.save(user.get());

            cart.forEach((item) ->{
                cartResponse.add(ListingResponse.builder()
                        .id(item.getId())
                        .datePosted(item.getDatePosted())
                        .description(item.getDescription())
                        .title(item.getTitle())
                        .sellerId(item.getSeller().getId())
                        .price(item.getPrice())
                        .build());
            });
        }else{
            errors.add("User not found");
        }
        return Response.builder().node(mapToJson(cartResponse)).errors(errors).build();
    }

    public Response getUser(String userId) {
        Optional<User> user = userRepository.findById(userId);
        List<String> errors = new LinkedList<>();
        UserProfile profile = null;
        if(user.isPresent()){
            User found = user.get();
            profile = UserProfile.builder()
                    .firstName(found.getFirstName())
                    .lastName(found.getLastName())
                    .email(found.getEmail())
                    .license(found.getLicense())
                    .authorities(found.getAuthorities())
                    .profile(found.getProfile())
                    .twoFactorEnabled(found.isTwoFactorEnabled())
                    .build();
        } else{
            errors.add("User not found");
        }

        return Response.builder().node(mapToJson(profile)).errors(errors).build();
    }

    public Response updateProfile(String userId, PictureDTO pic){
        Optional<User> user = userRepository.findById(userId);
        List<String> errors = new LinkedList<>();
        UserProfile profile = null;
        if(user.isPresent()){
            User found = user.get();
            Picture picture = Picture.builder().picture(pic.getPicture()).build();

            pictureRepository.save(picture);
            found.setProfile(picture);
            userRepository.save(found);

            profile = UserProfile.builder()
                    .firstName(found.getFirstName())
                    .lastName(found.getLastName())
                    .email(found.getEmail())
                    .license(found.getLicense())
                    .authorities(found.getAuthorities())
                    .profile(found.getProfile())
                    .twoFactorEnabled(found.isTwoFactorEnabled())
                    .build();
        } else{
            errors.add("User not found");
        }
        return Response.builder().node(mapToJson(profile)).errors(errors).build();
    }

    /**
     * @param userId the user being updated
     * @param status the status of the 2 auth factor
     * @return the updated user
     */
    public Response updateTwoFactor(String userId, boolean status) {
        Optional<User> user = userRepository.findById(userId);
        List<String> errors = new LinkedList<>();
        UserProfile profile = null;
        if(user.isPresent()){

            User found = user.get();

            found.setTwoFactorEnabled(status);
            userRepository.save(found);

            profile = UserProfile.builder()
                    .firstName(found.getFirstName())
                    .lastName(found.getLastName())
                    .email(found.getEmail())
                    .license(found.getLicense())
                    .authorities(found.getAuthorities())
                    .profile(found.getProfile())
                    .twoFactorEnabled(found.isTwoFactorEnabled())
                    .build();
        } else{
            errors.add("User not found");
        }
        return Response.builder().node(mapToJson(profile)).errors(errors).build();
    }
}
