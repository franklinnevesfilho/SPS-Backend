package com.groupfour.snb.security.registration.token;

import com.groupfour.snb.models.user.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name="REGISTRATION_TOKENS")
public class RegistrationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private UUID tokenId;
    @NonNull
    @Column(name = "token_created-at")
    private LocalDateTime createdAt;
    @NonNull
    @Column(name = "token_expires-at")
    private LocalDateTime expiresAt;
    @Nullable
    @Column(name = "token_confirmed-at")
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public RegistrationToken(){
        this.createdAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusMinutes(15);
    }

}

