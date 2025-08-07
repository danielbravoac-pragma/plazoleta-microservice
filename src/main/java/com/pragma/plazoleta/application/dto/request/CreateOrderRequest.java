package com.pragma.plazoleta.application.dto.request;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    @NotNull
    private Long customerId;

    private Long employeeId;

    @NotNull
    @JsonManagedReference
    private List<CreateOrderDetailsRequest> details;

    @NotNull
    private Integer total;

    @NotNull
    private Integer restaurantId;
}
