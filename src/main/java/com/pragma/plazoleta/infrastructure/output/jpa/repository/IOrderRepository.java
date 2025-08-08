package com.pragma.plazoleta.infrastructure.output.jpa.repository;

import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IOrderRepository extends IGenericRepository<OrderEntity, Long> {
    @EntityGraph(attributePaths = "details")
    @Query("""
            SELECT o FROM OrderEntity o
            JOIN o.statuses s
            WHERE s.dateRegistration = (
                SELECT MAX(s2.dateRegistration) FROM OrderStatusEntity s2
                WHERE s2.order.id = o.id
            )
            AND s.status.id = :statusId
            AND o.restaurant.id = :restaurantId
            """)
    Page<OrderEntity> findOrdersWithLatestStatus(@Param("statusId") Long statusId,
                                                 @Param("restaurantId") Long restaurantId,
                                                 Pageable pageable);

    @Modifying
    @Query("UPDATE OrderEntity o SET o.employeeId = :employeeId WHERE o.id = :orderId")
    void updateEmployeeIdByOrderId(@Param("orderId") Long orderId,
                                   @Param("employeeId") Long employeeId);


}
