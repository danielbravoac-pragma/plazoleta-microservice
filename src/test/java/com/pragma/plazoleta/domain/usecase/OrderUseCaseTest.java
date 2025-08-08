package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.exceptions.AccessDeniedException;
import com.pragma.plazoleta.application.exceptions.OrderInProgressException;
import com.pragma.plazoleta.application.exceptions.OrderPinInvalidException;
import com.pragma.plazoleta.domain.api.*;
import com.pragma.plazoleta.domain.model.*;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

 class OrderUseCaseTest {
    @Mock
    private IOrderPersistencePort orderPersistencePort;
    @Mock
    private IDishServicePort dishServicePort;
    @Mock
    private IRestaurantServicePort restaurantServicePort;
    @Mock
    private IStatusServicePort statusServicePort;
    @Mock
    private IOrderStatusServicePort orderStatusServicePort;
    @Mock
    private IUserServicePort userServicePort;
    @Mock
    private IMessageServicePort messageServicePort;

    private OrderUseCase orderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderUseCase = new OrderUseCase(
                orderPersistencePort,
                dishServicePort,
                restaurantServicePort,
                statusServicePort,
                orderStatusServicePort,
                userServicePort,
                messageServicePort
        );
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }
    @Test
    void saveOrder_shouldSaveCorrectlyWhenNoOrderInProgress() {
        Order order = new Order();
        order.setCustomerId(1L);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(10L);
        order.setRestaurant(restaurant);

        Dish dish = new Dish();
        dish.setId(1L);
        OrderDetail detail = new OrderDetail();
        detail.setDish(dish);
        order.setDetails(List.of(detail));

        Order savedOrder = new Order();
        savedOrder.setId(100L);

        Status pendingStatus = new Status(StatusEnum.PENDING);

        when(orderStatusServicePort.findActiveOrdersByCustomer(eq(1L), any())).thenReturn(List.of());
        when(restaurantServicePort.findById(10L)).thenReturn(restaurant);
        when(dishServicePort.findById(1L)).thenReturn(dish);
        when(orderPersistencePort.saveOrder(order)).thenReturn(savedOrder);
        when(statusServicePort.findByName("PENDING")).thenReturn(pendingStatus);

        Order result = orderUseCase.saveOrder(order);

        assertEquals(savedOrder.getId(), result.getId());
        verify(orderStatusServicePort).saveOrderStatus(savedOrder, pendingStatus);
    }

    @Test
    void saveOrder_shouldThrowExceptionWhenOrderInProgress() {
        Order order = new Order();
        order.setCustomerId(1L);

        when(orderStatusServicePort.findActiveOrdersByCustomer(eq(1L), any()))
                .thenReturn(List.of(new OrderStatus()));

        assertThrows(OrderInProgressException.class, () -> orderUseCase.saveOrder(order));
    }

    @Test
    void updateOrderStatus_shouldThrowAccessDenied_ifNotAssignedEmployee() {
        Long orderId = 1L, statusId = 2L;
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("3", null)
        );

        Order order = new Order();
        order.setId(orderId);
        order.setEmployeeId(99L); // otro usuario

        when(orderPersistencePort.findById(orderId)).thenReturn(order);
        when(userServicePort.findById(3L)).thenReturn(new User());

        Status status = new Status();
        status.setId(statusId);
        when(statusServicePort.findById(statusId)).thenReturn(status);
        when(orderStatusServicePort.findById(orderId, statusId)).thenReturn(null);

        assertThrows(AccessDeniedException.class, () -> orderUseCase.updateOrderStatus(orderId, statusId));
    }

    @Test
    void setDeliveredOrder_shouldThrowException_ifPinInvalid() {
        Order order = new Order();
        order.setId(1L);
        order.setDeliveryPin("123456");

        Status done = new Status();
        done.setId(2L);
        Status delivered = new Status();
        delivered.setId(3L);

        when(statusServicePort.findByName("DONE")).thenReturn(done);
        when(statusServicePort.findByName("DELIVERED")).thenReturn(delivered);
        when(orderPersistencePort.findById(1L)).thenReturn(order);
        when(orderStatusServicePort.findById(1L, 2L)).thenReturn(new OrderStatus());

        assertThrows(OrderPinInvalidException.class, () ->
                orderUseCase.setDeliveredOrder(1L, "999999")
        );
    }


    @Test
    void findOrdersWithLatestStatus_shouldReturnOrdersForRestaurant() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("5", null)
        );

        User user = new User();
        user.setId(5L);
        user.setRestaurantId(9L);

        Page<Order> page = new PageImpl<>(List.of(new Order()));

        when(userServicePort.findById(5L)).thenReturn(user);
        when(orderPersistencePort.findOrdersWithLatestStatus(2L, 9L, 0, 5)).thenReturn(page);

        Page<Order> result = orderUseCase.findOrdersWithLatestStatus(null, 0, 5);

        assertEquals(1, result.getContent().size());
        verify(orderPersistencePort).findOrdersWithLatestStatus(2L, 9L, 0, 5);
    }
}

