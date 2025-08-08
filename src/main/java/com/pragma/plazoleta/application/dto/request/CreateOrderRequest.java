package com.pragma.plazoleta.application.dto.request;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Esquema de Creacion de Orden")
public class CreateOrderRequest {

    @NotNull
    @Schema(description = "Cliente asociado a la orden", example = "1")
    private Long customerId;

    @Schema(description = "Empleado asociado a la orden", example = "1")
    private Long employeeId;

    @NotNull
    @JsonManagedReference
    @Schema(description = "Detalles asociado a la orden")
    private List<CreateOrderDetailsRequest> details;

    @NotNull
    @Schema(description = "Total de la orden",example = "200")
    private Integer total;

    @NotNull
    @Schema(description = "Restaurante asociado a la orden",example = "1")
    private Integer restaurantId;
}
