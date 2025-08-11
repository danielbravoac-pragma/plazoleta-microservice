package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.response.EmployeeAverageDurationResponse;
import com.pragma.plazoleta.application.dto.response.GetOrderDurationsResponse;
import com.pragma.plazoleta.application.mapper.ReportMapper;
import com.pragma.plazoleta.domain.api.IReportServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportHandler implements IReportHandler {

    private final IReportServicePort reportServicePort;
    private final ReportMapper reportMapper;

    @Override
    public List<GetOrderDurationsResponse> getOrderDurationsResponseList() {
        return reportMapper.toGetOrderDurationsResponseList(reportServicePort.getOrderDurations());
    }

    @Override
    public List<EmployeeAverageDurationResponse> getEmployeeAverageDurationResponse() {
        return reportMapper.toEmployeeAverageDurationResponseList(reportServicePort.getEmployeeAverageDurations());
    }
}
