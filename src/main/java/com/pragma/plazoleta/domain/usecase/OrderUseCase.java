package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.exceptions.OrderInProgressException;
import com.pragma.plazoleta.domain.api.*;
import com.pragma.plazoleta.domain.model.*;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IDishServicePort dishServicePort;

    private final IRestaurantServicePort restaurantServicePort;
    private final IStatusServicePort statusServicePort;
    private final IOrderStatusServicePort orderStatusServicePort;


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
}
