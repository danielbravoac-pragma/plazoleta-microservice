package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateOrderRequest;
import com.pragma.plazoleta.application.dto.response.CreateOrderResponse;
import com.pragma.plazoleta.application.dto.response.FindOrderResponse;
import com.pragma.plazoleta.application.dto.response.PageResponse;
import com.pragma.plazoleta.application.mapper.OrderMapper;
import com.pragma.plazoleta.domain.api.IOrderServicePort;
import com.pragma.plazoleta.domain.model.Order;
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
    private final OrderMapper orderMapper;

    @Override
    public CreateOrderResponse saveOrder(CreateOrderRequest createOrderRequest) {
        return orderMapper.toCreateOrderResponse(orderServicePort.saveOrder(
                orderMapper.toOrder(createOrderRequest)
        ));
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
}
