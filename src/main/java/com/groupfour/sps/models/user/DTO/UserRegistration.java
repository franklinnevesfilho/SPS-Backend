package com.groupfour.sps.models.user.DTO;

import com.groupfour.sps.models.interfaces.Validator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

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
@Builder
@Data
public class UserRegistration implements Validator {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Override
    public List<String> validate() {
        List<String> errors = new LinkedList<>();
        if(firstName.contains(" ")){
            errors.add("Invalid name, cannot contain spaces");
        }
        if(!email.contains("@")){
            errors.add("Invalid Email");
        }
        if(password == null || password.contains(" ")){
            errors.add("Invalid password... Make sure password does not contain spaces");
        }
        return errors;
    }
}
