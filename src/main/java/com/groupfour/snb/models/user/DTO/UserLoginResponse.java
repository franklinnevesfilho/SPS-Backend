package com.groupfour.snb.models.user.DTO;

import com.groupfour.snb.models.tokens.SessionToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserLoginResponse {
     private UserLogin user;
     private SessionToken sessionToken;
}
