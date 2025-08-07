package com.pragma.plazoleta.infrastructure.output.jpa.mapper;

import com.pragma.plazoleta.domain.model.OrderStatus;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderStatusEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderStatusId;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderStatusEntityMapper {
    OrderStatusEntity toOrderStatusEntity(OrderStatus orderStatus);

    OrderStatus toOrderStatus(OrderStatusEntity orderStatusEntity);

    List<OrderStatus> toOrderStatusList(List<OrderStatusEntity> orderStatusEntityList);

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
