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

import java.util.HashSet;
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
                Role adminRole = roleService.addRole(Role.builder().authority("ADMIN").build());
                roleService.addRole(Role.builder().authority("USER").build());
                roleService.addRole(Role.builder().authority("SELLER").build());
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);

                User user = User.builder()
                        .firstName("admin")
                        .lastName("admin")
                        .email("@admin")
                        .password(passwordEncoder.encode("password"))
                        .enabled(true)
                        .authorities(roles)
                        .build();

                User user1 = User.builder()
                        .email("@test")
                        .password(passwordEncoder.encode("password"))
                        .enabled(true)
                        .authorities(roles)
                        .build();

                userService.save(user);
                userService.save(user1);
                userService.sellerRequest(user.getId(), "fwenijwoefvbw");

               listingService.addListing(new CreateListing("title1","description1",10.00), user.getId());
               listingService.addListing(new CreateListing("title2","description2",15.00), user.getId());
               listingService.addListing(new CreateListing("title3","description3",25.00), user.getId());

                log.info("Finished building base users");
           }
        };

    }
}
