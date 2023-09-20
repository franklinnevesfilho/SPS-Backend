package com.groupfour.snb.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MainService {
    protected ObjectMapper mapper = new ObjectMapper();
    protected JsonNode mapToJson(Object obj){
        mapper.registerModule(new JavaTimeModule());
        return mapper.convertValue(obj, JsonNode.class);
    }
}
