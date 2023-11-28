package com.groupfour.sps.models.user.DTO;

import com.groupfour.sps.models.interfaces.Validator;
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
public class SellerRequest implements Validator {
    private String sellerId;

    @Override
    public List<String> validate() {
        List<String> errors = new LinkedList<>();
        if(sellerId == null || sellerId.isEmpty() || sellerId.isBlank()) {
            errors.add("Seller id nonexistent");
        }
        return errors;
    }
}
