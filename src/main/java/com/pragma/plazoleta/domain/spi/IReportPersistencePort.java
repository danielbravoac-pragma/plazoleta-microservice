package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.EmployeeAverageDuration;
import com.pragma.plazoleta.domain.model.OrderDuration;

import java.util.List;

public interface IReportPersistencePort {
    List<OrderDuration> getOrderDurations(Long restaurantId);
    List<EmployeeAverageDuration> getEmployeeAverageDurations(Long restaurantId);
}
