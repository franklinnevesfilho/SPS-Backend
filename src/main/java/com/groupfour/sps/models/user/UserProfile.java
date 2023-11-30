package com.groupfour.sps.models.user;

import com.groupfour.sps.models.Picture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * <h1>DTO</h1>
 * Info needed for the profile
 * @author Franklin Neves
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserProfile {
    private String email;
    private String firstName;
    private String lastName;
    private String license;
    private Set<Role> authorities;
    private Picture profile;
    private boolean twoFactorEnabled;
}
