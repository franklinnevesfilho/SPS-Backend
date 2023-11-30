package com.groupfour.sps.models.tokens;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Used to send the token
 * @author Franklin Neves
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegistrationTokenDTO {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
}
