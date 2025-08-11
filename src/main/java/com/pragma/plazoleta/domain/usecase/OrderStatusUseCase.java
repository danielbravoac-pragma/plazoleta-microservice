package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.exceptions.AccessDeniedException;
import com.pragma.plazoleta.domain.api.IOrderStatusServicePort;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderStatus;
import com.pragma.plazoleta.domain.model.Status;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;
import com.pragma.plazoleta.domain.spi.IOrderStatusPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class OrderStatusUseCase implements IOrderStatusServicePort {

    private final IOrderStatusPersistencePort orderStatusPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;

    @Override
    public void saveOrderStatus(Order order, Status status) {
        orderStatusPersistencePort.saveOrderStatus(order, status);
    }

    @Override
    public List<OrderStatus> findActiveOrdersByCustomer(Long customerId, List<String> statusList) {
        return orderStatusPersistencePort.findActiveOrdersByCustomer(customerId, statusList);
    }

    @Override
    public OrderStatus findById(Long idOrder, Long idStatus) {
        return orderStatusPersistencePort.findById(idOrder, idStatus);
    }

    @Override
    public List<OrderStatus> findOrdersByOrderId(Long orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long loggedUserId = Long.valueOf((String) auth.getPrincipal());

        Order order=orderPersistencePort.findById(orderId);

        if(!Objects.equals(order.getCustomerId(), loggedUserId)){
            throw new AccessDeniedException("You can't check status of an order that is not yours");
        }

        return orderStatusPersistencePort.findOrdersByOrderId(orderId);
    }
}
