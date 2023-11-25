package com.groupfour.sps.models.listing;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.groupfour.sps.models.listing.attributes.Image;
import com.groupfour.sps.models.listing.attributes.Message;
import com.groupfour.sps.models.user.User;
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
@Builder
@Data
@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "listing")
    private List<Message> messages;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "listing")
    private List<Image> images;

    @Builder.Default
    private LocalDate datePosted = LocalDate.now();

    private LocalDate datePurchased;

    public Listing purchased() {
        this.datePurchased = LocalDate.now();
        return this;
    }
}

