package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.infrastructure.exception.DataNotFoundException;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IOrderEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderJpaAdapterTest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IOrderEntityMapper orderEntityMapper;

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    private OrderJpaAdapter orderJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderJpaAdapter = new OrderJpaAdapter(orderRepository, orderEntityMapper, restaurantServicePort);
    }

    @Test
    void saveOrder_shouldReturnMappedOrder() {
        Order order = new Order();
        OrderEntity entity = new OrderEntity();
        OrderEntity savedEntity = new OrderEntity();
        Order mappedOrder = new Order();

        when(orderEntityMapper.toOrderEntity(order)).thenReturn(entity);
        when(orderRepository.save(entity)).thenReturn(savedEntity);
        when(orderEntityMapper.toOrder(savedEntity)).thenReturn(mappedOrder);

        Order result = orderJpaAdapter.saveOrder(order);

        assertEquals(mappedOrder, result);
        verify(orderRepository).save(entity);
    }

    @Test
    void findOrdersWithLatestStatus_shouldReturnMappedPage() {
        Long statusId = 1L;
        Long restaurantId = 2L;
        int page = 0;
        int size = 5;

        OrderEntity entity = new OrderEntity();
        Order order = new Order();

        Page<OrderEntity> entityPage = new PageImpl<>(List.of(entity), PageRequest.of(page, size), 1);

        when(orderRepository.findOrdersWithLatestStatus(statusId, restaurantId, PageRequest.of(page, size)))
                .thenReturn(entityPage);
        when(orderEntityMapper.toOrderList(List.of(entity))).thenReturn(List.of(order));

        Page<Order> result = orderJpaAdapter.findOrdersWithLatestStatus(statusId, restaurantId, page, size);

        assertEquals(1, result.getContent().size());
        assertEquals(order, result.getContent().get(0));
    }

    @Test
    void findById_shouldReturnOrder_whenFound() {
        Long id = 10L;
        OrderEntity entity = new OrderEntity();
        Order order = new Order();

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(orderEntityMapper.toOrder(entity)).thenReturn(order);

        Order result = orderJpaAdapter.findById(id);

        assertEquals(order, result);
        verify(orderRepository).findById(id);
    }

    @Test
    void findById_shouldThrow_whenNotFound() {
        Long id = 10L;
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> orderJpaAdapter.findById(id));
    }

    @Test
    void updateEmployeeId_shouldCallRepository() {
        Long orderId = 5L;
        Long employeeId = 99L;

        orderJpaAdapter.updateEmployeeId(orderId, employeeId);

        verify(orderRepository).updateEmployeeIdByOrderId(orderId, employeeId);
    }

    @Test
    void updatePin_shouldCallRepository() {
        Long orderId = 5L;
        String pin = "123456";

        orderJpaAdapter.updatePin(orderId, pin);

        verify(orderRepository).updateDeliveryPinByOrderId(orderId, pin);
    }
}
