package com.pragma.plazoleta.application.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDishRequest {
    @NotNull
    private Long dishId;

    @NotNull
    @Min(value = 0, message = "Debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 0, message = "Debe ser un número entero")
    private Integer price;

    @NotBlank
    private String description;
}
