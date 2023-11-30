package com.groupfour.sps.models.tokens;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>This class is used to store the TwoAuthFactor Token</h1>
 * @author Franklin Neves Filho
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class TwoFactorToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String token;
    @Column(length = 10000)
    private String jwt;
}
