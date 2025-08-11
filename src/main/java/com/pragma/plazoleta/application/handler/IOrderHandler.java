package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateOrderRequest;
import com.pragma.plazoleta.application.dto.response.*;

import java.util.List;

public interface IOrderHandler {
    CreateOrderResponse saveOrder(CreateOrderRequest createOrderRequest);

    PageResponse<FindOrderResponse> findOrdersWithLatestStatus(Long statusId, Integer page, Integer size);

    UpdateStatusOrderResponse assignEmployeeAndPutInProgress(Long idOrder);

    UpdateStatusOrderResponse setDone(Long idOrder);

    UpdateStatusOrderResponse cancelOrder(Long idOrder);

    UpdateStatusOrderResponse deliveredOrder(Long idOrder, String pin);

    List<GetOrderDetailTraceabilityResponse> getDetailOrder(Long idOrder);
}
