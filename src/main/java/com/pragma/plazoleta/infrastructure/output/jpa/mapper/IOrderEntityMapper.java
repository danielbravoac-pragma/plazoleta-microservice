package com.pragma.plazoleta.infrastructure.output.jpa.mapper;


import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderStatus;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderStatusEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderStatusId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderEntityMapper {
    Order toOrder(OrderEntity orderEntity);

    OrderEntity toOrderEntity(Order order);

    @Mapping(target = "order", ignore = true)
    OrderStatusEntity orderStatusToOrderStatusEntity(OrderStatus status);

    default Long map(OrderStatusId id) {
        return id != null ? id.getStatusId() : null;
    }

    default OrderStatusId map(Long id) {
        if (id == null) return null;
        OrderStatusId orderStatusId = new OrderStatusId();
        orderStatusId.setStatusId(id);
        return orderStatusId;
    }
}
