package com.groupfour.snb.services;
import com.groupfour.snb.models.User;
import com.groupfour.snb.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }

    public User addUser(User user){
        userRepository.save(user);
        return user;
    }

    public User getUserById(UUID id){
        return userRepository.findById(id).orElseThrow();
    }

    public Iterable<User> getAll() {
         return userRepository.findAll();
    }
}
