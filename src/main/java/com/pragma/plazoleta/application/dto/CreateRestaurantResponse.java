package com.pragma.plazoleta.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta de creación de restaurante.")
public class CreateRestaurantResponse {
    @Schema(description = "Nombre del restaurante", example = "Pizzeria Trattoria")
    private String name;
    @Schema(description = "NIT del Comercio", example = "123456789632587-8")
    private String nit;
    @Schema(description = "Direccion del restaurante", example = "Calle Salame y Queso Fresco 123")
    private String address;
    @Schema(description = "Número de teléfono asociado al restaurante", example = "+579638527412")
    private String phoneNumber;
    @Schema(description = "Identificador del Propietario del Restaurante", example = "1")
    private Long ownerId;
}
