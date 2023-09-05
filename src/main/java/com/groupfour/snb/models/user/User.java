package com.groupfour.snb.models.user;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


// Using lombok to generate constructors
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name="user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String email;
    @Nullable
    private UserRole role;

    @Nullable
    private UUID verificationCode;
    public User(){
        role = UserRole.BUYER;
    }
}
