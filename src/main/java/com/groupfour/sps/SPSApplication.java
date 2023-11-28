package com.groupfour.sps;

import com.groupfour.sps.models.listing.DTO.CreateListing;
import com.groupfour.sps.models.user.Role;
import com.groupfour.sps.models.user.User;
import com.groupfour.sps.services.ListingService;
import com.groupfour.sps.services.RoleService;
import com.groupfour.sps.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Slf4j
@SpringBootApplication
public class SPSApplication {
    public static void main(String[] args) {
        SpringApplication.run(SPSApplication.class, args);
    }


    @Bean
    CommandLineRunner run(RoleService roleService, UserService userService, ListingService listingService, PasswordEncoder passwordEncoder) {
        return args -> {
            // Only to be used when Database is in update mode
           if (!roleService.isAuthorityPresent("ADMIN")) {
                Role adminRole  =  roleService.addRole(Role.builder().authority("ADMIN").build());
                Role userRole   =  roleService.addRole(Role.builder().authority("USER").build());
                Role sellerRole =  roleService.addRole(Role.builder().authority("SELLER").build());

                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(adminRole);
                adminRoles.add(userRole);

                Set<Role> sellerRoles = new HashSet<>();
                sellerRoles.add(sellerRole);
                sellerRoles.add(userRole);

                // Admin user
                User user = User.builder()
                        .firstName("admin")
                        .lastName("admin")
                        .email("admin@email.edu")
                        .password(passwordEncoder.encode("adminPassword"))
                        .enabled(true)
                        .authorities(adminRoles)
                        .build();

                userService.save(user);

               DecimalFormat decimalFormat = new DecimalFormat("#.##");

                // Create 20 fake sellers
                for(int i = 0; i < 20 ;i++){
                    User fakeUser = User.builder()
                            .firstName("Faker")
                            .lastName("seller " + i)
                            .email("faker"+i+"@email.edu")
                            .password(passwordEncoder.encode("password"))
                            .license("license "+i)
                            .enabled(true)
                            .authorities(sellerRoles)
                            .build();
                    userService.save(fakeUser);

                    // every seller has 5 listings
                    for (int j = 0; j < 5; j++) {
                        Double randomPrice = new Random().nextDouble(50);
                        listingService.addListing(new CreateListing("Book "+j,"Seller "+i+ "'s "+ j + " book", Double.parseDouble(decimalFormat.format(randomPrice))), fakeUser.getId());
                    }
                }

               log.info("Finished building base users");
           }else{
               log.info("System up and running");
           }
        };

    }
}
