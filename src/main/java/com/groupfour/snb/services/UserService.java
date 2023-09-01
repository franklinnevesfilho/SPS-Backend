package com.groupfour.snb.services;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.models.user.UserRole;
import com.groupfour.snb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final User[] users ={
            new User(
                    "Franklin",
                    "Neves",
                    "franklin.email@gmail.com",
                    UserRole.ADMIN),
            new User(
                    "Daniel",
                    "Cortina",
                    "dcortina.email@gmail.com",
                    UserRole.BUYER),
            new User(
                    "Sapphire",
                    "Mervilus",
                    "smervilus.email@gmail.com",
                    UserRole.SELLER)

    };

    public void defineUsers(){
        userRepository.saveAll(Arrays.asList(users));
    }

    public Optional<User> getUser(Integer userId){
        return userRepository.findById(userId);
    }

    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }
}
