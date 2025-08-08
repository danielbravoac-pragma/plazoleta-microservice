package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.exceptions.AccessDeniedException;
import com.pragma.plazoleta.application.exceptions.OrderInProgressException;
import com.pragma.plazoleta.application.exceptions.OrderPinInvalidException;
import com.pragma.plazoleta.domain.api.*;
import com.pragma.plazoleta.domain.model.*;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IDishServicePort dishServicePort;

    private final IRestaurantServicePort restaurantServicePort;
    private final IStatusServicePort statusServicePort;
    private final IOrderStatusServicePort orderStatusServicePort;

    private final IUserServicePort userServicePort;

    private static final SecureRandom secureRandom = new SecureRandom();


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
    public Order updateOrderStatus(Long idOrder, Long idStatus) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long loggedUserId = Long.valueOf((String) auth.getPrincipal());
        User user = userServicePort.findById(loggedUserId);

        Order order = orderPersistencePort.findById(idOrder);
        Status status = statusServicePort.findById(idStatus);

        if (orderStatusServicePort.findById(idOrder, idStatus) != null) {
            throw new OrderInProgressException("Order is already registered in this status");
        }

        if (order.getEmployeeId() == null) {
            orderPersistencePort.updateEmployeeId(order.getId(), user.getId());
            order.setEmployeeId(user.getId());
        } else if (!Objects.equals(order.getEmployeeId(), user.getId())) {
            throw new AccessDeniedException("You can't update this order. You´re not the employee assigned");
        }

        orderStatusServicePort.saveOrderStatus(order, status);
        return order;
    }

    @Override
    public Order assignAndPutInProgress(Long idOrder) {
        Status statusInProgress = statusServicePort.findByName(StatusEnum.IN_PROGRESS.toString());
        Status statusCancelled = statusServicePort.findByName(StatusEnum.CANCELLED.toString());
        Status statusDelivered = statusServicePort.findByName(StatusEnum.DELIVERED.toString());
        Order order = orderPersistencePort.findById(idOrder);

        if (orderStatusServicePort.findById(order.getId(), statusDelivered.getId()) != null ||
                orderStatusServicePort.findById(order.getId(), statusCancelled.getId()) != null) {
            throw new OrderInProgressException("Your order have an invalid status");
        }

        return updateOrderStatus(idOrder, statusInProgress.getId());
    }

    @Override
    public Order setDoneAndAssignPin(Long idOrder) {
        Status status = statusServicePort.findByName(StatusEnum.DONE.toString());
        Status statusInProgress = statusServicePort.findByName(StatusEnum.IN_PROGRESS.toString());
        Status statusDelivered = statusServicePort.findByName(StatusEnum.DELIVERED.toString());
        Status statusCancelled = statusServicePort.findByName(StatusEnum.CANCELLED.toString());
        Order order = orderPersistencePort.findById(idOrder);

        if (orderStatusServicePort.findById(order.getId(), statusInProgress.getId()) == null ||
                orderStatusServicePort.findById(order.getId(), statusDelivered.getId()) != null ||
                orderStatusServicePort.findById(order.getId(), statusCancelled.getId()) != null) {
            throw new OrderInProgressException("Your order have an invalid status");
        }

        String pin = String.valueOf(secureRandom.nextInt(900000) + 100000);
        orderPersistencePort.updatePin(order.getId(), pin);
        return updateOrderStatus(order.getId(), status.getId());
    }

    @Override
    public Order setCancelOrder(Long idOrder) {
        Status statusInProgress = statusServicePort.findByName(StatusEnum.IN_PROGRESS.toString());
        Status statusDone = statusServicePort.findByName(StatusEnum.DONE.toString());
        Status statusDelivered = statusServicePort.findByName(StatusEnum.DELIVERED.toString());
        Status statusCancelled = statusServicePort.findByName(StatusEnum.CANCELLED.toString());

        Order order = orderPersistencePort.findById(idOrder);

        if (orderStatusServicePort.findById(order.getId(), statusInProgress.getId()) != null ||
                orderStatusServicePort.findById(order.getId(), statusDone.getId()) != null ||
                orderStatusServicePort.findById(order.getId(), statusDelivered.getId()) != null
        ) {
            throw new OrderInProgressException("Your order have an invalid status");
        }

        return updateOrderStatus(order.getId(), statusCancelled.getId());
    }

    @Override
    public Order setDeliveredOrder(Long idOrder, String pin) {
        Status statusDone = statusServicePort.findByName(StatusEnum.DONE.toString());
        Status statusDelivered = statusServicePort.findByName(StatusEnum.DELIVERED.toString());

        Order order = orderPersistencePort.findById(idOrder);

        if (orderStatusServicePort.findById(order.getId(), statusDone.getId()) == null) {
            throw new OrderInProgressException("Your order have an invalid status");
        }

        if (!order.getDeliveryPin().equals(pin)) {
            throw new OrderPinInvalidException("Your PIN is invalid");
        }

        return updateOrderStatus(order.getId(), statusDelivered.getId());
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
