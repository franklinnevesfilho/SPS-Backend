package com.groupfour.snb.models.interfaces;

import java.io.Serializable;
public interface UserInfo extends Serializable {
    String getPassword();
    String getEmail();
}
