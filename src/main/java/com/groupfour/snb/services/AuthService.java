package com.groupfour.snb.services;

import com.groupfour.snb.models.Role;
import com.groupfour.snb.models.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class AuthService {

    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private RoleService roleService;


    public User registerUser(String email, String password){
        String encodedPassword = passwordEncoder.encode(password);

        Role userRole = roleService.getRoleByAuthority("USER");
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        return userService.addUser(new User(email,encodedPassword,authorities));
    }


}
