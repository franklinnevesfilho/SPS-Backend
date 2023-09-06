package com.groupfour.snb.models.user;
import com.groupfour.snb.models.Listing;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;


// Using lombok to generate constructors
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name="USERS")
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @NonNull
    @Column(name = "user_first-name")
    private String firstName;
    @NonNull
    @Column(name = "user_last-name")
    private String lastName;
    @NonNull
    @Column(name = "user_email")
    private String email;
    @Nullable
    @Column(name = "user_password")
    private String password;
    @OneToMany
    private List<Listing> listings;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;
    @NonNull
    @Column(name = "user_is-activated")
    private boolean activated = false;

    public User(){
        role = UserRole.BUYER;
    }
}
