package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.EmployeeAverageDuration;
import com.pragma.plazoleta.domain.model.OrderDuration;

import java.util.List;

public interface IReportServicePort {
    List<OrderDuration> getOrderDurations();

    List<EmployeeAverageDuration> getEmployeeAverageDurations();
}
