package com.pragma.plazoleta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDishResponse {
    private String name;
    private Integer price;
    private String description;
}
