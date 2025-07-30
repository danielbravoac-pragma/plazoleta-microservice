package com.pragma.plazoleta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantResponse {
    private String name;
    private String nit;
    private String address;
    private String phoneNumber;
    private Long ownerId;
}
