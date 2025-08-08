package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.exceptions.OrderInProgressException;
import com.pragma.plazoleta.domain.api.*;
import com.pragma.plazoleta.domain.model.*;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Transient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IDishServicePort dishServicePort;

    private final IRestaurantServicePort restaurantServicePort;
    private final IStatusServicePort statusServicePort;
    private final IOrderStatusServicePort orderStatusServicePort;

    private final IUserServicePort userServicePort;


    @Override
    public Order saveOrder(Order order) {
        List<String> statusActive = List.of(
                StatusEnum.IN_PROGRESS.toString(),
                StatusEnum.DONE.toString(),
                StatusEnum.PENDING.toString()
        );

        List<OrderStatus> ordersInProcess = orderStatusServicePort.findActiveOrdersByCustomer(
                order.getCustomerId(),
                statusActive
        );

        if (!ordersInProcess.isEmpty()) {
            throw new OrderInProgressException("You have an order in progress");
        }

        Restaurant restaurant = restaurantServicePort.findById(order.getRestaurant().getId());
        order.setRestaurant(restaurant);

        order.getDetails().forEach(orderDetail -> {
            Dish dish = dishServicePort.findById(orderDetail.getDish().getId());
            orderDetail.setDish(dish);
        });

        Order savedOrder = orderPersistencePort.saveOrder(order);

        order.setStatuses(List.of(new Status(StatusEnum.PENDING)));

        order.getStatuses().forEach(status -> {
                    Status statusFull = statusServicePort.findByName(StatusEnum.PENDING.toString());
                    orderStatusServicePort.saveOrderStatus(savedOrder, statusFull);
                }
        );

        return savedOrder;
    }

    @Override
    public Page<Order> findOrdersWithLatestStatus(Long statusId, Integer page, Integer size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long loggedUserId = Long.valueOf((String) auth.getPrincipal());
        User user = userServicePort.findById(loggedUserId);

        if (statusId == null) {
            statusId = 2L;
        }

        return orderPersistencePort.findOrdersWithLatestStatus(statusId, user.getRestaurantId(), page, size);
    }
}
