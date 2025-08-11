package com.pragma.plazoleta.application.dto.response;

import com.pragma.plazoleta.domain.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindOrderResponse {
    private Long id;
    private Long customerId;
    private Long employeeId;
    private List<FindOrderDetailResponse> details;
    private Integer total;
    private Long restaurantId;
}
