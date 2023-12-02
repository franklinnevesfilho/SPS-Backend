package com.groupfour.sps.models;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Franklin Neves
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Response {
    private JsonNode node;
    @Builder.Default
    private List<String> errors = new LinkedList<>();
}
