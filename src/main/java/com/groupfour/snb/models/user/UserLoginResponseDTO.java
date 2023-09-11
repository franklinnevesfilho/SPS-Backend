package com.groupfour.snb.models.user;

import com.groupfour.snb.models.tokens.SessionToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserLoginResponseDTO {
     private UserLoginDTO user;
     private SessionToken sessionToken;
}
