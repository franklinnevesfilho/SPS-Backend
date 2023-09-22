package com.groupfour.snb.utils.security;

import com.groupfour.snb.models.interfaces.UserInfo;
import com.groupfour.snb.models.interfaces.Validator;
import com.groupfour.snb.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SecurityUser implements UserInfo, Authentication, Validator {
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
        return user.getFirstName();
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
    public List<String> validate() {
        return new LinkedList<>();
    }

    @Override
    public String getName() {
        return user.getEmail();
    }
}
