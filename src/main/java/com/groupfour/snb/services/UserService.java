package com.groupfour.snb.services;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.repositories.UserRepository;
import jakarta.mail.MessagingException;
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

    public void addUser(User user) throws MessagingException {
       user.setVerificationCode(UUID.randomUUID());
       emailService.sendVerificationEmail(user);
       userRepository.save(user);
    }
}
