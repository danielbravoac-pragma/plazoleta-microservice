package com.pragma.plazoleta.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Solicitud de actualización de platos.")
public class UpdateDishRequest {
    @NotNull
    @Schema(description = "Identificador de plato a actualizar", example = "1")
    private Long id;

    @NotNull
    @Min(value = 0, message = "Debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 0, message = "Debe ser un número entero")
    @Schema(description = "Precio del plato, entero mayor a 0", example = "85")
    private Integer price;

    @NotBlank
    @Schema(description = "Descripción del plato", example = "Deliciosa Pizza con Salame a la parrilla y  bordes de Queso Parmesano.")
    private String description;
}
