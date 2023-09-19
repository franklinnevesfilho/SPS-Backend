package com.groupfour.snb.models.user.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.groupfour.snb.models.Validator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserLoginDTO implements Validator {
    private String email;
    private String password;

    @Override
    public List<String> validate() {
        List<String> result = new LinkedList<>();
        if(email == null || !email.contains("@")){
            result.add("Invalid email");
        }
        if(password == null || password.contains(" ")){
            result.add("Invalid password... password cannot contain spaces");
        }
        return result;
    }
}
