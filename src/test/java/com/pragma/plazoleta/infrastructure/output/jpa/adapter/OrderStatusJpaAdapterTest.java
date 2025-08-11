package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderStatus;
import com.pragma.plazoleta.domain.model.Status;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderStatusEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderStatusId;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.StatusEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IOrderEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IOrderStatusEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IStatusEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IOrderStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class OrderStatusJpaAdapterTest {

    @Mock private IOrderEntityMapper orderEntityMapper;
    @Mock private IStatusEntityMapper statusEntityMapper;
    @Mock private IOrderStatusEntityMapper orderStatusEntityMapper;
    @Mock private IOrderStatusRepository orderStatusRepository;

    private OrderStatusJpaAdapter orderStatusJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderStatusJpaAdapter = new OrderStatusJpaAdapter(
                orderEntityMapper,
                statusEntityMapper,
                orderStatusEntityMapper,
                orderStatusRepository
        );
    }

    @Test
    void saveOrderStatus_shouldSaveMappedEntity() {
        Order order = new Order();
        order.setId(1L);
        Status status = new Status();
        status.setId(2L);

        OrderEntity orderEntity = new OrderEntity();
        StatusEntity statusEntity = new StatusEntity();

        when(orderEntityMapper.toOrderEntity(order)).thenReturn(orderEntity);
        when(statusEntityMapper.toStatusEntity(status)).thenReturn(statusEntity);

        orderStatusJpaAdapter.saveOrderStatus(order, status);

        verify(orderStatusRepository).save(argThat(saved -> {
            OrderStatusId expectedId = new OrderStatusId(1L, 2L);
            return saved.getId().equals(expectedId) &&
                    saved.getOrder().equals(orderEntity) &&
                    saved.getStatus().equals(statusEntity);
        }));
    }

    @Test
    void findActiveOrdersByCustomer_shouldReturnMappedList() {
        Long customerId = 1L;
        List<String> statusList = List.of("PENDING");

        List<OrderStatusEntity> entityList = List.of(new OrderStatusEntity());
        List<OrderStatus> modelList = List.of(new OrderStatus());

        when(orderStatusRepository.findActiveOrdersByCustomer(customerId, statusList)).thenReturn(entityList);
        when(orderStatusEntityMapper.toOrderStatusList(entityList)).thenReturn(modelList);

        List<OrderStatus> result = orderStatusJpaAdapter.findActiveOrdersByCustomer(customerId, statusList);

        assertEquals(modelList, result);
        verify(orderStatusRepository).findActiveOrdersByCustomer(customerId, statusList);
    }

    @Test
    void findById_shouldReturnMappedOrderStatusIfExists() {
        Long orderId = 1L;
        Long statusId = 2L;
        OrderStatusEntity entity = new OrderStatusEntity();
        OrderStatus model = new OrderStatus();

        when(orderStatusRepository.findById(new OrderStatusId(orderId, statusId)))
                .thenReturn(Optional.of(entity));
        when(orderStatusEntityMapper.toOrderStatus(entity)).thenReturn(model);

        OrderStatus result = orderStatusJpaAdapter.findById(orderId, statusId);

        assertEquals(model, result);
        verify(orderStatusRepository).findById(new OrderStatusId(orderId, statusId));
    }

    @Test
    void findById_shouldReturnNullIfNotFound() {
        Long orderId = 1L;
        Long statusId = 2L;

        when(orderStatusRepository.findById(new OrderStatusId(orderId, statusId))).thenReturn(Optional.empty());
        when(orderStatusEntityMapper.toOrderStatus(null)).thenReturn(null);

        OrderStatus result = orderStatusJpaAdapter.findById(orderId, statusId);

        assertNull(result);
    }

    @Test
    void findOrdersByOrderId_shouldReturnMappedList() {
        Long orderId = 5L;

        List<OrderStatusEntity> entityList = List.of(new OrderStatusEntity());
        List<OrderStatus> modelList = List.of(new OrderStatus());

        when(orderStatusRepository.findOrdersByOrderId(orderId)).thenReturn(entityList);
        when(orderStatusEntityMapper.toOrderStatusList(entityList)).thenReturn(modelList);

        List<OrderStatus> result = orderStatusJpaAdapter.findOrdersByOrderId(orderId);

        assertEquals(modelList, result);
        verify(orderStatusRepository).findOrdersByOrderId(orderId);
    }
}
