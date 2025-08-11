package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateOrderRequest;
import com.pragma.plazoleta.application.dto.response.*;
import com.pragma.plazoleta.application.mapper.OrderMapper;
import com.pragma.plazoleta.domain.api.IOrderServicePort;
import com.pragma.plazoleta.domain.api.IOrderStatusServicePort;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.StatusEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderStatusServicePort orderStatusServicePort;
    private final OrderMapper orderMapper;

    @Override
    public CreateOrderResponse saveOrder(CreateOrderRequest createOrderRequest) {
        CreateOrderResponse createOrderResponse = orderMapper.toCreateOrderResponse(orderServicePort.saveOrder(
                orderMapper.toOrder(createOrderRequest)
        ));
        createOrderResponse.setStatus(StatusEnum.PENDING.name());
        return createOrderResponse;
    }

    @Override
    public PageResponse<FindOrderResponse> findOrdersWithLatestStatus(Long statusId, Integer page, Integer size) {
        Page<Order> pageOrder = orderServicePort.findOrdersWithLatestStatus(statusId, page, size);
        List<FindOrderResponse> content = orderMapper.toFindOrderResponseList(pageOrder.getContent());

        PageResponse<FindOrderResponse> response = new PageResponse<>();
        response.setContent(content);
        response.setPage(pageOrder.getNumber());
        response.setSize(pageOrder.getSize());
        response.setTotalElements(pageOrder.getTotalElements());
        response.setTotalPages(pageOrder.getTotalPages());
        response.setLast(pageOrder.isLast());

        return response;
    }

    @Override
    public UpdateStatusOrderResponse assignEmployeeAndPutInProgress(Long idOrder) {
        UpdateStatusOrderResponse updateStatusOrderResponse = orderMapper.toUpdateStatusOrderResponse(orderServicePort.assignAndPutInProgress(idOrder));
        updateStatusOrderResponse.setStatus(StatusEnum.IN_PROGRESS.name());
        return updateStatusOrderResponse;
    }

    @Override
    public UpdateStatusOrderResponse setDone(Long idOrder) {
        UpdateStatusOrderResponse doneOrder = orderMapper.toUpdateStatusOrderResponse(
                orderServicePort.setDoneAndAssignPin(idOrder)
        );
        doneOrder.setStatus(StatusEnum.DONE.name());
        return doneOrder;
    }

    @Override
    public UpdateStatusOrderResponse cancelOrder(Long idOrder) {
        UpdateStatusOrderResponse cancelledOrder = orderMapper.toUpdateStatusOrderResponse(
                orderServicePort.setCancelOrder(idOrder)
        );
        cancelledOrder.setStatus(StatusEnum.CANCELLED.name());
        return cancelledOrder;
    }

    @Override
    public UpdateStatusOrderResponse deliveredOrder(Long idOrder, String pin) {
        UpdateStatusOrderResponse deliveredOrder = orderMapper.toUpdateStatusOrderResponse(
                orderServicePort.setDeliveredOrder(idOrder, pin)
        );
        deliveredOrder.setStatus(StatusEnum.DELIVERED.name());
        return deliveredOrder;
    }

    @Override
    public List<GetOrderDetailTraceabilityResponse> getDetailOrder(Long idOrder) {
        return orderMapper.toGetOrderDetailTraceabilityResponseList(
                orderStatusServicePort.findOrdersByOrderId(idOrder)
        );
    }
}
