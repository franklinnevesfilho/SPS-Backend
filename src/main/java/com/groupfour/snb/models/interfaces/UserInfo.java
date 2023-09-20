package com.groupfour.snb.models.interfaces;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public interface UserInfo extends Serializable {

    String getPassword();
    String getEmail();

    Collection<? extends GrantedAuthority> getAuthorities();
    boolean isEnabled();
}
