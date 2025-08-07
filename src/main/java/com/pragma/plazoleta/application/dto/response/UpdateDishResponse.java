package com.pragma.plazoleta.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Respuesta de actualización de plato.")
public class UpdateDishResponse {
    @Schema(description = "Nombre del plato", example = "Pizza Americana")
    private String name;
    @Schema(description = "Precio del plato, entero mayor a 0", example = "85")
    private Integer price;
    @Schema(description = "Descripción del plato", example = "Deliciosa Pizza con Salame a la parrilla y  bordes de Queso Parmesano.")
    private String description;
}
