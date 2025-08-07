package com.pragma.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private Long customerId;
    private Long employeeId;
    private List<Status> statuses;
    private List<OrderDetail> details;
    private Integer total;
    private Restaurant restaurant;
}
