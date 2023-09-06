package com.groupfour.snb.services;
import com.groupfour.snb.models.Listing;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private final User[] users ={
            new User(
                    "Franklin",
                    "Neves",
                    "franklin.email@gmail.com"),
            new User(
                    "Daniel",
                    "Cortina",
                    "dcortina.email@gmail.com"),
            new User(
                    "Sapphire",
                    "Mervilus",
                    "smervilus.email@gmail.com")

    };

    public void defineUsers(){
        userRepository.saveAll(Arrays.asList(users));
    }
    public Optional<User> getUser(UUID userId){
        return userRepository.findById(userId);
    }
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }
    public void addUser(User user) { userRepository.save(user); }
    public void activateUser(User user) {
        user.setActivated(true);
        userRepository.save(user);
    }
    public Optional<User> getUserByUUID(UUID id) {
        return userRepository.findById(id);
    }

    public Iterable<Listing> getAllListingsFromUser(UUID userId) {
        return userRepository.findById(userId).get().getListings();
    }
}
