package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateOrderRequest;
import com.pragma.plazoleta.application.dto.response.CreateOrderResponse;
import com.pragma.plazoleta.application.mapper.OrderMapper;
import com.pragma.plazoleta.domain.api.IOrderServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final OrderMapper orderMapper;

    @Override
    public CreateOrderResponse saveOrder(CreateOrderRequest createOrderRequest) {
        return orderMapper.toCreateOrderResponse(orderServicePort.saveOrder(
                orderMapper.toOrder(createOrderRequest)
        ));
    }
}
