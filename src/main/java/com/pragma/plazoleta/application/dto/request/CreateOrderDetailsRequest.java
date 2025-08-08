package com.pragma.plazoleta.application.dto.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Detalle de la orden")
public class CreateOrderDetailsRequest {
    @NotNull
    @Schema(description = "Id de Platillo",example = "1")
    private Long dishId;

    @NotNull
    @Schema(description = "Cantidad de platillos",example = "2")
    private Integer quantity;

    @NotNull
    @Schema(description = "Subtotal de platillo",example = "200")
    private Integer subTotal;

    @JsonBackReference
    private CreateOrderRequest order;
}
