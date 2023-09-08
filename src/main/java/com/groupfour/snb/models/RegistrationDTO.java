package com.groupfour.snb.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Registration DTO</h1>
 * DTO stands for Data Transfer Object.
 * <p>
 * This object is used as a middle ground for the authentication process
 * to minimize the user data being passed around
 *
 * @author Franklin Neves Filho
 * @Last-Modified: 09/08/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistrationDTO {
    private String email;
    private String password;

    public String toString(){
        return "Registration info: " + email + " " + password;
    }
}
