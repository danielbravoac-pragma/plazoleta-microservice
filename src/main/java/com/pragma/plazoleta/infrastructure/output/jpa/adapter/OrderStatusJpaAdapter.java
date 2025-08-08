package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderStatus;
import com.pragma.plazoleta.domain.model.Status;
import com.pragma.plazoleta.domain.spi.IOrderStatusPersistencePort;
import com.pragma.plazoleta.infrastructure.exception.DataNotFoundException;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderStatusEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderStatusId;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IOrderEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IOrderStatusEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IStatusEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IOrderStatusRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderStatusJpaAdapter implements IOrderStatusPersistencePort {

    private final IOrderEntityMapper orderEntityMapper;
    private final IStatusEntityMapper statusEntityMapper;
    private final IOrderStatusEntityMapper orderStatusEntityMapper;

    private final IOrderStatusRepository orderStatusRepository;

    @Override
    public void saveOrderStatus(Order order, Status status) {
        OrderStatusId id = new OrderStatusId(order.getId(), status.getId());
        OrderStatusEntity relation = new OrderStatusEntity();
        relation.setId(id);
        relation.setOrder(orderEntityMapper.toOrderEntity(order));
        relation.setStatus(statusEntityMapper.toStatusEntity(status));

        orderStatusRepository.save(relation);
    }

    @Override
    public List<OrderStatus> findActiveOrdersByCustomer(Long customerId, List<String> statusList) {
        return orderStatusEntityMapper.toOrderStatusList(
                orderStatusRepository.findActiveOrdersByCustomer(customerId, statusList)
        );
    }

    @Override
    public OrderStatus findById(Long idOrder, Long idStatus) {
        return orderStatusEntityMapper.toOrderStatus(
                orderStatusRepository.findById(new OrderStatusId(idOrder, idStatus)).orElse(null)
        );
    }
}
