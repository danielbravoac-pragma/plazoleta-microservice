package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.EmployeeAverageDurationResponse;
import com.pragma.plazoleta.application.dto.response.GetOrderDurationsResponse;
import com.pragma.plazoleta.domain.model.EmployeeAverageDuration;
import com.pragma.plazoleta.domain.model.OrderDuration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ReportMapper {
    EmployeeAverageDurationResponse toEmployeeAverageDurationResponse(EmployeeAverageDuration employeeAverageDuration);
    GetOrderDurationsResponse toGetOrderDurationsResponse(OrderDuration orderDuration);

    List<EmployeeAverageDurationResponse> toEmployeeAverageDurationResponseList(List<EmployeeAverageDuration> employeeAverageDuration);
    List<GetOrderDurationsResponse> toGetOrderDurationsResponseList(List<OrderDuration> orderDuration);
}
