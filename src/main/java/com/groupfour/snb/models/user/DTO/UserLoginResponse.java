package com.groupfour.snb.models.user.DTO;

import com.groupfour.snb.models.tokens.SessionToken;
import com.groupfour.snb.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserLoginResponse {
     private User user;
     private SessionToken sessionToken;
}
