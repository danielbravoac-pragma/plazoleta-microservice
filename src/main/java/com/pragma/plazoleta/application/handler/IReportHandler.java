package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.response.EmployeeAverageDurationResponse;
import com.pragma.plazoleta.application.dto.response.GetOrderDurationsResponse;

import java.util.List;

public interface IReportHandler {
    List<GetOrderDurationsResponse> getOrderDurationsResponseList();

    List<EmployeeAverageDurationResponse> getEmployeeAverageDurationResponse();
}
