package com.groupfour.snb.models.tokens;

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

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "expires_at")
    private LocalDateTime expiresAt = createdAt.plusMinutes(15);

    @OneToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

}

