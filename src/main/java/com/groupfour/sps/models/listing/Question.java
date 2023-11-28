package com.groupfour.sps.models.listing;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.groupfour.sps.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private Listing listing;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Builder.Default
    private LocalDate createdAt  = LocalDate.now();
    private String question;
    private String answer;
}

