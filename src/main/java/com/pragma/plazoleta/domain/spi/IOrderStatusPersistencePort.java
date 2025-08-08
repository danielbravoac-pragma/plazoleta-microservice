package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderStatus;
import com.pragma.plazoleta.domain.model.Status;

import java.util.List;

public interface IOrderStatusPersistencePort {
    void saveOrderStatus(Order order, Status status);

    List<OrderStatus> findActiveOrdersByCustomer(Long customerId, List<String> statusList);

    OrderStatus findById(Long idOrder, Long idStatus);
}
