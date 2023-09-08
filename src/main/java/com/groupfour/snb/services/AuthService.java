package com.groupfour.snb.services;

import com.groupfour.snb.models.Role;
import com.groupfour.snb.models.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;


    public User registerUser(String email, String password){
        String encodedPassword = passwordEncoder.encode(password);

        Role userRole = roleService.getRoleByAuthority("USER");
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        return userService.addUser(new User(email,encodedPassword,authorities));
    }


}
