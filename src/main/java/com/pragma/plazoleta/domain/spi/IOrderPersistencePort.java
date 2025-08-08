package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.Order;
import org.springframework.data.domain.Page;

public interface IOrderPersistencePort {
    Order saveOrder(Order order);

    Page<Order> findOrdersWithLatestStatus(Long statusId, Long restaurantId, Integer page, Integer size);

    Order findById(Long id);

    void updateEmployeeId(Long idOrder, Long idEmployee);

    void updatePin(Long idOrder, String pin);
}
