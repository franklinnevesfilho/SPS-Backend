package com.groupfour.sps.models.user.DTO;

import com.groupfour.sps.models.tokens.RegistrationTokenDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRegistrationResponse {
    private UserRegistration user;
    private RegistrationTokenDTO registrationToken;
}
