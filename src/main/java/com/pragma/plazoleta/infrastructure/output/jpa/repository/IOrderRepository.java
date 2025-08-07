package com.pragma.plazoleta.infrastructure.output.jpa.repository;

import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IOrderRepository extends IGenericRepository<OrderEntity, Long> {
    @Query("""
            SELECT os.order FROM OrderStatusEntity os
            WHERE os.dateRegistration = (
               SELECT MAX(innerOs.dateRegistration)
               FROM OrderStatusEntity innerOs
               WHERE innerOs.order.id = os.order.id
            )
            AND (:status IS NULL OR os.status.id = :statusId)
            """)
    Page<OrderEntity> findOrdersWithLatestStatus(@Param("statusId") Long statusId, Pageable pageable);
}
