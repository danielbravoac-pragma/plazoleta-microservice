package com.pragma.plazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindOrderDetailResponse {
    private Long id;
    private Long dishId;
    private Integer quantity;
    private Integer subTotal;
}
