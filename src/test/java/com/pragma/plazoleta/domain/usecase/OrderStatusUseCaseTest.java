package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.exceptions.AccessDeniedException;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderStatus;
import com.pragma.plazoleta.domain.model.Status;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;
import com.pragma.plazoleta.domain.spi.IOrderStatusPersistencePort;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderStatusUseCaseTest {

    @Mock
    private IOrderStatusPersistencePort orderStatusPersistencePort;

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    private OrderStatusUseCase orderStatusUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderStatusUseCase = new OrderStatusUseCase(orderStatusPersistencePort, orderPersistencePort);
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testSaveOrderStatus_CallsPersistencePort() {
        Order order = new Order();
        Status status = new Status();

        orderStatusUseCase.saveOrderStatus(order, status);

        verify(orderStatusPersistencePort).saveOrderStatus(order, status);
    }

    @Test
    void testFindActiveOrdersByCustomer_CallsPersistencePort() {
        Long customerId = 1L;
        List<String> statuses = List.of("PENDING", "IN_PROGRESS");
        List<OrderStatus> expected = List.of(new OrderStatus());

        when(orderStatusPersistencePort.findActiveOrdersByCustomer(customerId, statuses)).thenReturn(expected);

        List<OrderStatus> result = orderStatusUseCase.findActiveOrdersByCustomer(customerId, statuses);

        assertEquals(expected, result);
        verify(orderStatusPersistencePort).findActiveOrdersByCustomer(customerId, statuses);
    }

    @Test
    void testFindById_CallsPersistencePort() {
        Long orderId = 1L;
        Long statusId = 2L;
        OrderStatus expected = new OrderStatus();

        when(orderStatusPersistencePort.findById(orderId, statusId)).thenReturn(expected);

        OrderStatus result = orderStatusUseCase.findById(orderId, statusId);

        assertEquals(expected, result);
        verify(orderStatusPersistencePort).findById(orderId, statusId);
    }

    @Test
    void testFindOrdersByOrderId_WhenUserIsOwner_ReturnsList() {
        // Arrange
        Long userId = 99L;
        Long orderId = 100L;

        Order mockOrder = new Order();
        mockOrder.setCustomerId(userId);

        List<OrderStatus> expectedList = List.of(new OrderStatus(), new OrderStatus());

        // Simular usuario autenticado
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(String.valueOf(userId), null)
        );

        when(orderPersistencePort.findById(orderId)).thenReturn(mockOrder);
        when(orderStatusPersistencePort.findOrdersByOrderId(orderId)).thenReturn(expectedList);

        // Act
        List<OrderStatus> result = orderStatusUseCase.findOrdersByOrderId(orderId);

        // Assert
        assertEquals(expectedList, result);
        verify(orderPersistencePort).findById(orderId);
        verify(orderStatusPersistencePort).findOrdersByOrderId(orderId);
    }

    @Test
    void testFindOrdersByOrderId_WhenUserIsNotOwner_ThrowsAccessDenied() {
        // Arrange
        Long loggedUserId = 1L;
        Long orderId = 123L;

        Order order = new Order();
        order.setCustomerId(2L); // No es el dueño

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(String.valueOf(loggedUserId), null)
        );

        when(orderPersistencePort.findById(orderId)).thenReturn(order);

        // Act & Assert
        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () ->
                orderStatusUseCase.findOrdersByOrderId(orderId)
        );

        assertEquals("You can't check status of an order that is not yours", ex.getMessage());
        verify(orderPersistencePort).findById(orderId);
        verify(orderStatusPersistencePort, never()).findOrdersByOrderId(anyLong());
    }
}
