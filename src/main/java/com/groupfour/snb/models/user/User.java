package com.groupfour.snb.models.user;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.models.listing.Message;
import com.groupfour.snb.models.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name="users")
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="user_first-name")
    private String firstName;

    @Column(name="user_last-name")
    private String lastName;

    @Column(
            unique = true,
            name = "user_email")
    private String email;

    @Column(name = "user_password")
    @JsonIgnore
    private String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Listing> listings = new LinkedList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userSold")
    private List<Listing> purchasedListings = new LinkedList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @JsonIgnore
    private List<Message> messages = new LinkedList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_junction",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities = new HashSet<>();

    private boolean enabled = false;

}
