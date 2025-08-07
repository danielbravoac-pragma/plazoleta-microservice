package com.pragma.plazoleta.application.dto.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDetailsRequest {
    @NotNull
    private Long dishId;
    @NotNull
    private Integer quantity;
    @NotNull
    private Integer subTotal;

    @JsonBackReference
    private CreateOrderRequest order;
}
