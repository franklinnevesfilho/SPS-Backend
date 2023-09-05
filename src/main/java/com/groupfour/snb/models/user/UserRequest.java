package com.groupfour.snb.models.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public record UserRequest(String firstName, String lastName, String email) {
}
