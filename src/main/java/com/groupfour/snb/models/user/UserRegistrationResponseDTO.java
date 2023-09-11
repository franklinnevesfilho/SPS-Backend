package com.groupfour.snb.models.user;

import com.groupfour.snb.models.tokens.RegistrationTokenDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRegistrationResponseDTO {
    private UserRegistrationDTO user;
    private RegistrationTokenDTO registrationToken;
}
