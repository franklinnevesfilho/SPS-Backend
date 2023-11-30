package com.groupfour.sps.models.user;

import com.groupfour.sps.models.interfaces.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


/**
 * <h1>Security User class</h1>
 * To enable Spring security you must have a class that implements
 * Authentication, so that when receiving data you are able to authenticate that specific user
 *
 * @author Franklin Neves
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SecurityUser implements UserInfo, Authentication{
    private User user;
    @Builder.Default
    private boolean authenticated = false;

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getDetails() {
        return user.getEmail();
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return user.getId();
    }
}
