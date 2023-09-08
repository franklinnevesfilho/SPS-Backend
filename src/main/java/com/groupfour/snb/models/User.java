package com.groupfour.snb.models;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


// Using lombok to generate constructors

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Column(name="user_first-name")
    private String firstName;

    @Column(name="user_last-name")
    private String lastName;

    @Column(
            unique = true,
            name = "user_email")
    private String email;

    // This hides password
    // @JsonIgnore
    @Column(name = "user_password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "posted_user_id", referencedColumnName = "user_id")
    private Set<Listing> postedListings = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchased_user_id", referencedColumnName = "user_id")
    private Set<Listing> purchasedListings = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_junction",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities = new HashSet<>();

    public User(String email, String password, Set<Role> authorities) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void postListing(Listing listing) {
        this.postedListings.add(listing);
    }

    public void purchaseListing(Listing listing) {
        this.purchasedListings.add(listing.purchased());
    }
}
