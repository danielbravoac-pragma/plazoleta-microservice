package com.pragma.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    private Long id;
    private String name;
    private String nit;
    private String address;
    private String phoneNumber;
    private String logoUrl;
    private Long ownerId;
    private List<Dish> dishes;
}
