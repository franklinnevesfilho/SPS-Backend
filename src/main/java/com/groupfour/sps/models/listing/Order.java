package com.groupfour.sps.models.listing;

import com.groupfour.sps.models.interfaces.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Fuxinyang Chang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Validator {
    private Double price;
    private String currency;
    private String method;
    private String intent;
    private String description;

    @Override
    public List<String> validate() {
        List<String> errors = new LinkedList<>();
        if(price == null) {
            errors.add("Missing price");
        }
        if(currency.isEmpty() || currency.isBlank()){
            errors.add("missing currency");
        }
        if(method.isEmpty() || method.isBlank()){
            errors.add("missing method");
        }
        if(intent.isEmpty() || intent.isBlank()){
            errors.add("missing intent");

        }
        if(description.isEmpty() || description.isBlank()){
            errors.add("missing description");
        }

        return errors;
    }
}
