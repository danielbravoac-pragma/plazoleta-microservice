package com.pragma.plazoleta.infrastructure.output.jpa.repository;

import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderStatusEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderStatusId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrderStatusRepository extends IGenericRepository<OrderStatusEntity, OrderStatusId> {

    @Query(""" 
            SELECT os FROM OrderStatusEntity os
            WHERE os.order.customerId = :customerId
            AND os.status.name IN :statusList
            """
    )
    List<OrderStatusEntity> findActiveOrdersByCustomer(
            @Param("customerId") Long customerId,
            @Param("statusList") List<String> statusList
    );
}
