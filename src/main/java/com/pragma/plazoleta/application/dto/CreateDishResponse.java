package com.pragma.plazoleta.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Respuesta de creación de plato.")
public class CreateDishResponse {
    @Schema(description = "Nombre del plato", example = "Pizza Americana")
    private String name;
    @Schema(description = "Precio del plato, entero mayor a 0", example = "35")
    private Integer price;
    @Schema(description = "Descripción del plato", example = "Deliciosa Pizza con bordes de queso parmesano.")
    private String description;
}
