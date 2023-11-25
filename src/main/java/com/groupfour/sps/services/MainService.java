package com.groupfour.sps.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MainService {
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    protected JsonNode mapToJson(Object obj) {
        mapper.registerModule(new JavaTimeModule());
        return mapper.convertValue(obj, JsonNode.class);
    }
}
