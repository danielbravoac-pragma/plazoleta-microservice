package com.pragma.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String imageUrl;
    private Set<Category> categories;
    private Restaurant restaurant;
    private Boolean isActive;
}
