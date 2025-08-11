package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Order;
import org.springframework.data.domain.Page;

public interface IOrderServicePort {
    Order saveOrder(Order order);

    Order updateOrderStatus(Long idOrder, Long idStatus);

    Order assignAndPutInProgress(Long idOrder);

    Order setDoneAndAssignPin(Long idOrder);

    Order setCancelOrder(Long idOrder);

    Order setDeliveredOrder(Long idOrder,String pin);

    Page<Order> findOrdersWithLatestStatus(Long statusId, Integer page, Integer size);
}
