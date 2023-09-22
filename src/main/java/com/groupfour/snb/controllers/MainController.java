package com.groupfour.snb.controllers;

import com.groupfour.snb.models.interfaces.Validator;
import com.groupfour.snb.utils.responses.Response;
import com.groupfour.snb.utils.responses.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <h1>MainController</h1>
 * All other controller classes will extend this class
 * This class is responsible for generating the responses sent to the server
 * @author Franklin Neves Filho
 */
public class MainController {
    @Autowired
    protected ResponseFactory factory;
    /**
     *
     * @param supply Supplier method
     * @return An instance of ResponseEntity<Response>
     */
    public ResponseEntity<Response> genericGetAll(Supplier<Response> supply){
        ResponseEntity<Response> responseEntity;
        Response result = supply.get();
        if(result == null){
            responseEntity = factory.generateNoContentResponse();
        }else{
            responseEntity = factory.generateOkResponse(supply.get());
        }
        return responseEntity;
    }
    public ResponseEntity<Response> genericGetByTwoParameter(BiFunction<String, Object, Response> function, String parameter, Object object){
        ResponseEntity<Response> responseEntity;
        if(parameter != null && !parameter.isEmpty() && object != null){
            Response response = function.apply(parameter,object);
            if(response == null){
                responseEntity = factory.generateNoContentResponse();
            }else{
                responseEntity = factory.generateOkResponse(response);
            }
        }else{
            responseEntity = factory.generateBadRequest();
        }
        return responseEntity;
    }
    /**
     *
     * @param function a Function
     * @param parameter the param for the function
     * @return An instance of ResponseEntity<Response>
     */
    public ResponseEntity<Response> genericGetByParameter(Function<String,Response> function, String parameter){
        ResponseEntity<Response> responseEntity;
        if(parameter != null && !parameter.isEmpty()){
            Response response = function.apply(parameter);
            if(response == null){
                responseEntity = factory.generateNoContentResponse();
            }else{
                responseEntity = factory.generateOkResponse(response);
            }
        }else{
            responseEntity = factory.generateBadRequest();
        }
        return responseEntity;
    }
    /**
     * @param function A function
     * @param validator The checks if all parameters are valid
     * @return An instance of ResponseEntity<Response>
     *
     * The first check is to make sure all parameters are valid, then the second it to make sure nothing went wrong within the service.
     */
    public ResponseEntity<Response> genericRequest(Function<Validator,Response> function, Validator validator){
        ResponseEntity<Response> responseEntity;
        List<String> errors = validator.validate();
        if(errors == null || errors.isEmpty()){
            Response response = function.apply(validator);
            if(response == null){
                responseEntity = factory.generateNoContentResponse();
            }
            else{
                errors = response.getErrors();
                if(errors == null || errors.isEmpty()){
                    responseEntity = factory.generateOkResponse(response);
                }else{
                    responseEntity = factory.generateBadRequest(errors);
                }
            }
        }
        else{
            responseEntity = factory.generateBadRequest(errors);
        }
        return responseEntity;
    }
}
