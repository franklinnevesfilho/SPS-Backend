package com.groupfour.snb.models.tokens;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.groupfour.snb.models.user.DTO.UserLoginDTO;
import com.groupfour.snb.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="session_tokens")
public class SessionToken {

    @Transient
    private static final int DEFAULT_EXPIRATION_INTERVAL = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();


    @OneToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonBackReference
    private User user;

    public LocalDateTime getExpiresAt() {
        return createdAt.plusMinutes(DEFAULT_EXPIRATION_INTERVAL);
    }

    public boolean hasExpired() {
        return getExpiresAt().isBefore(LocalDateTime.now());
    }
}

