package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IOrderEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IRestaurantServicePort restaurantServicePort;

    @Override
    public Order saveOrder(Order order) {
        return orderEntityMapper.toOrder(orderRepository.save(orderEntityMapper.toOrderEntity(order)));
    }

    @Override
    public Page<Order> findOrdersWithLatestStatus(Long statusId, Long restaurantId, Integer page, Integer size) {
        Page<OrderEntity> orderEntityPage = orderRepository.findOrdersWithLatestStatus(statusId, restaurantId, PageRequest.of(page, size));

        return new Page<Order>() {
            @Override
            public int getTotalPages() {
                return orderEntityPage.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return orderEntityPage.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super Order, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return orderEntityPage.getNumber();
            }

            @Override
            public int getSize() {
                return orderEntityPage.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Order> getContent() {
                return orderEntityMapper.toOrderList(orderEntityPage.getContent());
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return orderEntityPage.isLast();
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Order> iterator() {
                return null;
            }
        };
    }
}
