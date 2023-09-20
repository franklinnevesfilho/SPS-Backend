package com.groupfour.snb.models.user.DTO;

import com.groupfour.snb.models.tokens.RegistrationTokenDTO;
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
