package com.groupfour.sps.models.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserLoginResponse {
     private String firstName;
     private String lastName;
     private boolean twoFactorEnabled;
     private String jwt;
}
