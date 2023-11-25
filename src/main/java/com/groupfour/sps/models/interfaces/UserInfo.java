package com.groupfour.sps.models.interfaces;

import java.io.Serializable;
public interface UserInfo extends Serializable {
    String getPassword();
    String getEmail();
}
