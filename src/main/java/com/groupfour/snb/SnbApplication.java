package com.groupfour.snb;

import com.groupfour.snb.models.Role;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.services.RoleService;
import com.groupfour.snb.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    CommandLineRunner run(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder){
        return args ->{

            //if(roleRepo.findByAuthority("ADMIN").isPresent()) return;

            Role adminRole = roleService.addRole(new Role("ADMIN"));
            roleService.addRole(new Role("USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            userService.addUser(
                    new User("admin",passwordEncoder.encode("password"),roles)
                );
        };
    }

}
