package com.groupfour.snb;

import com.groupfour.snb.models.listing.DTO.CreateListing;
import com.groupfour.snb.models.user.Role;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.services.ListingService;
import com.groupfour.snb.services.RoleService;
import com.groupfour.snb.services.UserService;
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
public class SnbApplication {
    public static void main(String[] args) {
        SpringApplication.run(SnbApplication.class, args);
    }


    @Bean
    CommandLineRunner run(RoleService roleService, UserService userService, ListingService listingService, PasswordEncoder passwordEncoder){
        return args ->{
            // Only to be used when Database is in update mode
            //if(roleService.findAuthority("ADMIN").isPresent()) return;
            Role adminRole = roleService.addRole(Role.builder().authority("ADMIN").build());

            roleService.addRole(Role.builder().authority("USER").build());
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

            userService.add(user);
            userService.add(user1);
            listingService.addListing(new CreateListing("Listing Title","Description"), user.getId());
            listingService.addListing(new CreateListing("Listing", "Description"), user1.getId());

            log.info("Finished building base users");
        };
    }

}
