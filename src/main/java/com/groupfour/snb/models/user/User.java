package com.groupfour.snb.models.user;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.models.listing.attributes.Message;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_listing_cart",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "listing_id")}
    )
    private List<Listing> cart = new LinkedList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_listing_wishlist",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "listing_id")}
    )
    private List<Listing> wishlist = new LinkedList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @JsonIgnore
    private List<Message> messages = new LinkedList<>();

    @OneToOne(mappedBy = "user")
    private VerifiedSeller sellerVerification;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_junction",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities = new HashSet<>();

    private boolean enabled = false;
    public String toString(){
        return "User:\n"+this.firstName+"\n"+this.lastName+"\n"+this.email+"\n"+this.password;
    }
}
