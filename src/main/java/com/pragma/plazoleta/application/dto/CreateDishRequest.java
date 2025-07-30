package com.pragma.plazoleta.application.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pragma.plazoleta.domain.model.Restaurant;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDishRequest {
    @NotBlank
    private String name;

    @NotNull
    @Min(value = 0, message = "Debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 0, message = "Debe ser un número entero")
    private Integer price;

    @NotBlank
    private String description;

    @NotNull
    private Long restaurantId;

    @NotBlank
    private String imageUrl;

    @NotNull
    private Set<CategoriesRequest> categories;
}
