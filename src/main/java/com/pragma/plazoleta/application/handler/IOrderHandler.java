package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateOrderRequest;
import com.pragma.plazoleta.application.dto.response.CreateOrderResponse;

public interface IOrderHandler {
    CreateOrderResponse saveOrder(CreateOrderRequest createOrderRequest);
}
