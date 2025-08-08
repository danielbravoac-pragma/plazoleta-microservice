package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateOrderRequest;
import com.pragma.plazoleta.application.dto.response.*;
import com.pragma.plazoleta.application.mapper.OrderMapper;
import com.pragma.plazoleta.domain.api.IOrderServicePort;
import com.pragma.plazoleta.domain.api.IOrderStatusServicePort;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderStatus;
import com.pragma.plazoleta.domain.model.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderHandlerTest {

    @Mock
    private IOrderServicePort orderServicePort;

    @Mock
    private IOrderStatusServicePort orderStatusServicePort;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderHandler orderHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveOrder() {
        CreateOrderRequest request = new CreateOrderRequest();
        Order domainOrder = new Order();
        Order savedOrder = new Order();
        CreateOrderResponse expectedResponse = new CreateOrderResponse();

        when(orderMapper.toOrder(request)).thenReturn(domainOrder);
        when(orderServicePort.saveOrder(domainOrder)).thenReturn(savedOrder);
        when(orderMapper.toCreateOrderResponse(savedOrder)).thenReturn(expectedResponse);

        CreateOrderResponse actualResponse = orderHandler.saveOrder(request);

        assertEquals(StatusEnum.PENDING.name(), actualResponse.getStatus());
        verify(orderMapper).toOrder(request);
        verify(orderServicePort).saveOrder(domainOrder);
        verify(orderMapper).toCreateOrderResponse(savedOrder);
    }

    @Test
    void testFindOrdersWithLatestStatus() {
        Long statusId = 1L;
        int page = 0, size = 2;
        Order order1 = new Order();
        Order order2 = new Order();
        Page<Order> pageResult = new PageImpl<>(List.of(order1, order2), PageRequest.of(page, size), 2);

        FindOrderResponse res1 = new FindOrderResponse();
        FindOrderResponse res2 = new FindOrderResponse();

        when(orderServicePort.findOrdersWithLatestStatus(statusId, page, size)).thenReturn(pageResult);
        when(orderMapper.toFindOrderResponseList(List.of(order1, order2))).thenReturn(List.of(res1, res2));

        PageResponse<FindOrderResponse> response = orderHandler.findOrdersWithLatestStatus(statusId, page, size);

        assertEquals(2, response.getContent().size());
        assertEquals(0, response.getPage());
        assertEquals(2, response.getSize());
        assertEquals(2, response.getTotalElements());
        assertEquals(1, response.getTotalPages());

        verify(orderServicePort).findOrdersWithLatestStatus(statusId, page, size);
        verify(orderMapper).toFindOrderResponseList(List.of(order1, order2));
    }

    @Test
    void testAssignEmployeeAndPutInProgress() {
        Long orderId = 1L;
        Order order = new Order();
        UpdateStatusOrderResponse response = new UpdateStatusOrderResponse();

        when(orderServicePort.assignAndPutInProgress(orderId)).thenReturn(order);
        when(orderMapper.toUpdateStatusOrderResponse(order)).thenReturn(response);

        UpdateStatusOrderResponse actual = orderHandler.assignEmployeeAndPutInProgress(orderId);

        assertEquals(StatusEnum.IN_PROGRESS.name(), actual.getStatus());
        verify(orderServicePort).assignAndPutInProgress(orderId);
        verify(orderMapper).toUpdateStatusOrderResponse(order);
    }

    @Test
    void testSetDone() {
        Long orderId = 2L;
        Order order = new Order();
        UpdateStatusOrderResponse response = new UpdateStatusOrderResponse();

        when(orderServicePort.setDoneAndAssignPin(orderId)).thenReturn(order);
        when(orderMapper.toUpdateStatusOrderResponse(order)).thenReturn(response);

        UpdateStatusOrderResponse actual = orderHandler.setDone(orderId);

        assertEquals(StatusEnum.DONE.name(), actual.getStatus());
        verify(orderServicePort).setDoneAndAssignPin(orderId);
        verify(orderMapper).toUpdateStatusOrderResponse(order);
    }

    @Test
    void testCancelOrder() {
        Long orderId = 3L;
        Order order = new Order();
        UpdateStatusOrderResponse response = new UpdateStatusOrderResponse();

        when(orderServicePort.setCancelOrder(orderId)).thenReturn(order);
        when(orderMapper.toUpdateStatusOrderResponse(order)).thenReturn(response);

        UpdateStatusOrderResponse actual = orderHandler.cancelOrder(orderId);

        assertEquals(StatusEnum.CANCELLED.name(), actual.getStatus());
        verify(orderServicePort).setCancelOrder(orderId);
        verify(orderMapper).toUpdateStatusOrderResponse(order);
    }

    @Test
    void testDeliveredOrder() {
        Long orderId = 4L;
        String pin = "1234";
        Order order = new Order();
        UpdateStatusOrderResponse response = new UpdateStatusOrderResponse();

        when(orderServicePort.setDeliveredOrder(orderId, pin)).thenReturn(order);
        when(orderMapper.toUpdateStatusOrderResponse(order)).thenReturn(response);

        UpdateStatusOrderResponse actual = orderHandler.deliveredOrder(orderId, pin);

        assertEquals(StatusEnum.DELIVERED.name(), actual.getStatus());
        verify(orderServicePort).setDeliveredOrder(orderId, pin);
        verify(orderMapper).toUpdateStatusOrderResponse(order);
    }

    @Test
    void testGetDetailOrder() {
        Long orderId = 5L;
        List<GetOrderDetailTraceabilityResponse> responses = List.of(
                new GetOrderDetailTraceabilityResponse(),
                new GetOrderDetailTraceabilityResponse()
        );

        when(orderStatusServicePort.findOrdersByOrderId(orderId)).thenReturn(List.of(new OrderStatus()));
        when(orderMapper.toGetOrderDetailTraceabilityResponseList(anyList())).thenReturn(responses);

        List<GetOrderDetailTraceabilityResponse> actual = orderHandler.getDetailOrder(orderId);

        assertEquals(2, actual.size());
        verify(orderStatusServicePort).findOrdersByOrderId(orderId);
        verify(orderMapper).toGetOrderDetailTraceabilityResponseList(anyList());
    }
}
