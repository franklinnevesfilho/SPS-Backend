package com.groupfour.snb;

import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.models.listing.Message;
import com.groupfour.snb.models.Role;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.services.ListingService;
import com.groupfour.snb.services.RoleService;
import com.groupfour.snb.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@RestController
public class SnbApplication {
    public static void main(String[] args) {
        SpringApplication.run(SnbApplication.class, args);
    }

    @GetMapping()
    public String mainMenu(){
        return "Welcome";
    }

    @Bean
    CommandLineRunner run(RoleService roleService, UserService userService, ListingService listingService, PasswordEncoder passwordEncoder){
        return args ->{
            // Only to be used when Database is in update mode
            //if(roleRepo.findByAuthority("ADMIN").isPresent()) return;
            Role adminRole = roleService.addRole(Role.builder().authority("ADMIN").build());
            roleService.addRole(Role.builder().authority("USER").build());


            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            User user = User.builder()
                                .email("admin")
                                .password(passwordEncoder.encode("password"))
                                .authorities(roles)
                                .build();

            userService.add(user);

            listingService.addListing(Listing.builder().user(user).title("Listing Title").datePosted(LocalDate.now()).description("Description").build());

            Listing listing = Listing.builder().userSold(user).title("Listing bought").datePosted(LocalDate.now()).description("Description").build();
            listingService.addListing(listing);
            listingService.addMessage(listing.getId(), Message.builder().message("Test Message").build());



        };
    }

}
