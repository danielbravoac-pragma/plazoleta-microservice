package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.request.CreateOrderDetailsRequest;
import com.pragma.plazoleta.application.dto.request.CreateOrderRequest;
import com.pragma.plazoleta.application.dto.response.*;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    CreateOrderResponse toCreateOrderResponse(Order order);

    @Mapping(source = "restaurantId", target = "restaurant.id")
    Order toOrder(CreateOrderRequest createOrderRequest);

    @Mapping(source = "dish.name", target = "dishName")
    CreateOrderDetailsResponse toCreateOrderDetailsResponse(OrderDetail orderDetail);

    @Mapping(source = "restaurant.id", target = "restaurantId")
    FindOrderResponse toFindOrderResponse(Order order);

    @Mapping(source = "dish.id", target = "dishId")
    FindOrderDetailResponse toFindOrderDetailResponse(OrderDetail orderDetail);

    UpdateStatusOrderResponse toUpdateStatusOrderResponse(Order order);

    List<FindOrderDetailResponse> toFindOrderDetailResponseList(List<OrderDetail> orderDetailList);

    @Mapping(target = "order", ignore = true)
    @Mapping(source = "dishId", target = "dish.id")
    OrderDetail toOrderDetail(CreateOrderDetailsRequest createOrderDetailsRequest);

    List<FindOrderResponse> toFindOrderResponseList(List<Order> orderList);

    List<OrderDetail> toOrderDetailList(List<CreateOrderDetailsRequest> details);

    List<CreateOrderDetailsResponse> toCreateOrderDetailsResponseList(List<OrderDetail> orderDetailList);


}
