package com.groupfour.sps.models.interfaces;

import java.util.List;

/**
 * This class allows for requests to be validated before running any operations
 * @author Franklin Neves
 */
public interface Validator {
    List<String> validate();
}
