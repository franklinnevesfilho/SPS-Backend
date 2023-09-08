package com.groupfour.snb.services;
import com.groupfour.snb.models.User;
import com.groupfour.snb.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



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
    public void activateUser(User user) {}

    public Iterable<User> getAll() {
         return userRepository.findAll();
    }
}
