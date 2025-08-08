package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateOrderRequest;
import com.pragma.plazoleta.application.dto.response.CreateOrderResponse;
import com.pragma.plazoleta.application.dto.response.FindOrderResponse;
import com.pragma.plazoleta.application.dto.response.PageResponse;
import com.pragma.plazoleta.application.dto.response.UpdateStatusOrderResponse;

public interface IOrderHandler {
    CreateOrderResponse saveOrder(CreateOrderRequest createOrderRequest);

    PageResponse<FindOrderResponse> findOrdersWithLatestStatus(Long statusId, Integer page, Integer size);

    UpdateStatusOrderResponse assignEmployeeAndPutInProgress(Long idOrder);
}
