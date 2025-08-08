package com.pragma.plazoleta.infrastructure.output.jpa.repository;

import com.pragma.plazoleta.infrastructure.output.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.projections.EmployeeAverageDurationProjection;
import com.pragma.plazoleta.infrastructure.output.jpa.projections.OrderDurationProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IReportRepository extends IGenericRepository<OrderEntity, Long> {

    @Query(value = """
                SELECT o.id_order AS orderId,
                    TIMESTAMPDIFF(SECOND, (SELECT MIN(s.date_registration) FROM order_status s WHERE s.order_id_order = o.id_order),
                        (SELECT MAX(s.date_registration) FROM order_status s WHERE s.order_id_order = o.id_order)
                    ) AS durationSeconds
                FROM orders o
                WHERE o.restaurant_id = :restaurantId
            """, nativeQuery = true)
    List<OrderDurationProjection> findOrderDurationsByRestaurant(@Param("restaurantId") Long restaurantId);

    @Query(value = """
                SELECT  o.employee_id AS employeeId,
                    AVG(TIMESTAMPDIFF(SECOND,  (SELECT MIN(s.date_registration) FROM order_status s WHERE s.order_id_order = o.id_order),
                        (SELECT MAX(s.date_registration) FROM order_status s WHERE s.order_id_order = o.id_order)
                    )) AS averageDurationSeconds
                FROM orders o
                WHERE o.employee_id IS NOT NULL AND o.restaurant_id = :restaurantId
                GROUP BY o.employee_id
                ORDER BY averageDurationSeconds ASC
            """, nativeQuery = true)
    List<EmployeeAverageDurationProjection> findEmployeeAveragesByRestaurant(@Param("restaurantId") Long restaurantId);
}

