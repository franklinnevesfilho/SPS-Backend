package com.groupfour.snb.models.listing;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.groupfour.snb.models.listing.attributes.Image;
import com.groupfour.snb.models.listing.attributes.Message;
import com.groupfour.snb.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
/**
 * <h1>Listing Model</h1>
 * This model is structured around listings that user will be able to sell on our applicaiton.
 * It will automatically generate a posted date once teh object is created,
 * and will generate the purchased date once purchased.
 *
 * @author Franklin Neves Filho
 * @Last-Modified: 09/08/2023
 */
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @ManyToOne
    @JoinColumn(name = "user_sold_id")
    private User userSold;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "listing")
    private List<Message> messages;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "listing")
    private List<Image> images;
    @Builder.Default
    private LocalDate datePosted = LocalDate.now();
    private LocalDate datePurchased;
    public Listing purchased(String userId) {
        this.datePurchased = LocalDate.now();
        this.userSold = User.builder().id(userId).build();
        return this;
    }
}

