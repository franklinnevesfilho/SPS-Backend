package com.groupfour.sps.models.interfaces;

import java.io.Serializable;

/**
 * This class is used as a way to get userInfo from several classes
 *
 * @author Franklin Neves
 */
public interface UserInfo extends Serializable {
    String getPassword();
    String getEmail();
}
