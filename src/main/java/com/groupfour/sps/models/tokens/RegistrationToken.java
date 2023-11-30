package com.groupfour.sps.models.tokens;

import com.groupfour.sps.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * The model for our registration tokens
 * @author Franklin Neves
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="registration_token")
public class RegistrationToken {

    private final static int TIME_TO_EXPIRE = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "token_id")
    private String id;

    @Transient
    @Builder.Default
    private LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(TIME_TO_EXPIRE);

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @OneToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    public boolean isExpired(){
        return expiresAt.isBefore(LocalDateTime.now());
    }
}
