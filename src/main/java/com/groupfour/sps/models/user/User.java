package com.groupfour.sps.models.user;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.groupfour.sps.models.listing.Listing;
import com.groupfour.sps.models.listing.attributes.Message;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column( unique = true,
             name = "email")
    private String email;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    private String license;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "seller_listings",
            joinColumns = {@JoinColumn(name = "seller_id")},
            inverseJoinColumns = {@JoinColumn(name = "listing_id")}
    )
    private List<Listing> postedListings = new LinkedList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_cart",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "listing_id")}
    )
    private List<Listing> cart = new LinkedList<>();

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
