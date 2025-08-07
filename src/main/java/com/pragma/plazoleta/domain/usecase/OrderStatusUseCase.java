package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IOrderStatusServicePort;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderStatus;
import com.pragma.plazoleta.domain.model.Status;
import com.pragma.plazoleta.domain.spi.IOrderStatusPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderStatusUseCase implements IOrderStatusServicePort {

    private final IOrderStatusPersistencePort orderStatusPersistencePort;

    @Override
    public void saveOrderStatus(Order order, Status status) {
        orderStatusPersistencePort.saveOrderStatus(order, status);
    }

    @Override
    public List<OrderStatus> findActiveOrdersByCustomer(Long customerId, List<String> statusList) {
        return orderStatusPersistencePort.findActiveOrdersByCustomer(customerId, statusList);
    }
}
