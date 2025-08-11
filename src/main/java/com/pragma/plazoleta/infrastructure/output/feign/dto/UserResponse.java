package com.pragma.plazoleta.infrastructure.output.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private List<String> roles;
    private Long restaurantId;
    private String phoneNumber;
}
