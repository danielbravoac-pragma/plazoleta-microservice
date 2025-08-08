package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.response.EmployeeAverageDurationResponse;
import com.pragma.plazoleta.application.dto.response.GetOrderDurationsResponse;
import com.pragma.plazoleta.application.mapper.ReportMapper;
import com.pragma.plazoleta.domain.api.IReportServicePort;
import com.pragma.plazoleta.domain.model.EmployeeAverageDuration;
import com.pragma.plazoleta.domain.model.OrderDuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReportHandlerTest {

    @Mock
    private IReportServicePort reportServicePort;

    @Mock
    private ReportMapper reportMapper;

    @InjectMocks
    private ReportHandler reportHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrderDurationsResponseList() {
        List<OrderDuration> domainList = List.of(new OrderDuration(), new OrderDuration());
        List<GetOrderDurationsResponse> responseList = List.of(new GetOrderDurationsResponse(), new GetOrderDurationsResponse());

        when(reportServicePort.getOrderDurations()).thenReturn(domainList);
        when(reportMapper.toGetOrderDurationsResponseList(domainList)).thenReturn(responseList);

        List<GetOrderDurationsResponse> result = reportHandler.getOrderDurationsResponseList();

        assertEquals(2, result.size());
        verify(reportServicePort).getOrderDurations();
        verify(reportMapper).toGetOrderDurationsResponseList(domainList);
    }

    @Test
    void testGetEmployeeAverageDurationResponse() {
        List<EmployeeAverageDuration> domainList = List.of(new EmployeeAverageDuration()); // Puedes reemplazar Object por el tipo real si tienes uno.
        List<EmployeeAverageDurationResponse> responseList = List.of(new EmployeeAverageDurationResponse(), new EmployeeAverageDurationResponse());

        when(reportServicePort.getEmployeeAverageDurations()).thenReturn(domainList);
        when(reportMapper.toEmployeeAverageDurationResponseList(domainList)).thenReturn(responseList);

        List<EmployeeAverageDurationResponse> result = reportHandler.getEmployeeAverageDurationResponse();

        assertEquals(2, result.size());
        verify(reportServicePort).getEmployeeAverageDurations();
        verify(reportMapper).toEmployeeAverageDurationResponseList(domainList);
    }
}
