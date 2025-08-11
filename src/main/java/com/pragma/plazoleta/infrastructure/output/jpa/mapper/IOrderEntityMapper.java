package com.pragma.plazoleta.infrastructure.output.jpa.mapper;


import com.pragma.plazoleta.domain.model.*;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.*;
import org.mapstruct.*;

import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderEntityMapper {

    @Mapping(target = "details", source = "details")
    @Mapping(target = "statuses", expression = "java(mapOrderStatusEntitiesToStatusList(orderEntity.getStatuses()))")
    Order toOrder(OrderEntity orderEntity);

    @Mapping(target = "details", source = "details")
    OrderEntity toOrderEntity(Order order);

    List<Order> toOrderList(List<OrderEntity> orderEntityList);

    @Mapping(target = "order", ignore = true)
    OrderDetail toOrderDetail(OrderDetailEntity entity);

    List<OrderDetail> orderDetailEntityListToOrderDetailList(List<OrderDetailEntity> list);


    @Mapping(target = "order", ignore = true)
    OrderDetailEntity toOrderDetailEntity(OrderDetail orderDetail);

    List<OrderDetailEntity> orderDetailListToOrderDetailEntityList(List<OrderDetail> list);

    @Mapping(target = "order", ignore = true)
    OrderStatusEntity orderStatusToOrderStatusEntity(OrderStatus status);

    default List<Status> mapOrderStatusEntitiesToStatusList(List<OrderStatusEntity> entities) {
        if (entities == null || entities.isEmpty()) return List.of();

        // Obtener el último por fecha (puedes cambiar a más complejo si necesitas)
        OrderStatusEntity last = entities.stream()
                .max(Comparator.comparing(OrderStatusEntity::getDateRegistration))
                .orElse(null);

        if (last == null) return List.of();

        Status status = new Status();
        status.setId(last.getStatus().getId());
        status.setName(StatusEnum.valueOf(last.getStatus().getName()));

        return List.of(status);
    }

    @Mapping(target = "dishes", ignore = true)
    Restaurant restaurantEntityToRestaurant(RestaurantEntity restaurantEntity);

    default Long map(OrderStatusId id) {
        return id != null ? id.getStatusId() : null;
    }

    default OrderStatusId map(Long id) {
        if (id == null) return null;
        OrderStatusId orderStatusId = new OrderStatusId();
        orderStatusId.setStatusId(id);
        return orderStatusId;
    }

    @AfterMapping
    default void setReverseRelations(@MappingTarget OrderEntity order) {
        if (order.getDetails() != null) {
            order.getDetails().forEach(d -> d.setOrder(order));
        }
    }
}
