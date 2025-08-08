package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Order;
import org.springframework.data.domain.Page;

public interface IOrderServicePort {
    Order saveOrder(Order order);

    Page<Order> findOrdersWithLatestStatus(Long statusId, Integer page, Integer size);
}
