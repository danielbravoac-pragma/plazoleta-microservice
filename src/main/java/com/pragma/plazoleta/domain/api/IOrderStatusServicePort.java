package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderStatus;
import com.pragma.plazoleta.domain.model.Status;

import java.util.List;

public interface IOrderStatusServicePort {
    void saveOrderStatus(Order order, Status status);

    List<OrderStatus> findActiveOrdersByCustomer(Long customerId, List<String> statusList);

    OrderStatus findById(Long idOrder, Long idStatus);

    List<OrderStatus> findOrdersByOrderId(Long orderId);
}
