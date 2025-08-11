package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.EmployeeAverageDuration;
import com.pragma.plazoleta.domain.model.OrderDuration;
import com.pragma.plazoleta.infrastructure.output.jpa.projections.EmployeeAverageDurationProjection;
import com.pragma.plazoleta.infrastructure.output.jpa.projections.OrderDurationProjection;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReportJpaAdapterTest {

    @Mock
    private IReportRepository reportRepository;

    private ReportJpaAdapter reportJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reportJpaAdapter = new ReportJpaAdapter(reportRepository);
    }

    @Test
    void getOrderDurations_shouldReturnMappedOrderDurations() {
        Long restaurantId = 1L;

        OrderDurationProjection projection1 = mock(OrderDurationProjection.class);
        when(projection1.getOrderId()).thenReturn(101L);
        when(projection1.getDurationSeconds()).thenReturn(150L);

        OrderDurationProjection projection2 = mock(OrderDurationProjection.class);
        when(projection2.getOrderId()).thenReturn(102L);
        when(projection2.getDurationSeconds()).thenReturn(300L);

        when(reportRepository.findOrderDurationsByRestaurant(restaurantId)).thenReturn(List.of(projection1, projection2));

        List<OrderDuration> result = reportJpaAdapter.getOrderDurations(restaurantId);

        assertEquals(2, result.size());
        assertEquals(101L, result.get(0).getOrderId());
        assertEquals(150L, result.get(0).getDurationSeconds());
        assertEquals(102L, result.get(1).getOrderId());
        assertEquals(300L, result.get(1).getDurationSeconds());

        verify(reportRepository).findOrderDurationsByRestaurant(restaurantId);
    }

    @Test
    void getEmployeeAverageDurations_shouldReturnMappedEmployeeDurations() {
        Long restaurantId = 2L;

        EmployeeAverageDurationProjection projection1 = mock(EmployeeAverageDurationProjection.class);
        when(projection1.getEmployeeId()).thenReturn(201L);
        when(projection1.getAverageDurationSeconds()).thenReturn(120L);

        EmployeeAverageDurationProjection projection2 = mock(EmployeeAverageDurationProjection.class);
        when(projection2.getEmployeeId()).thenReturn(202L);
        when(projection2.getAverageDurationSeconds()).thenReturn(80L);

        when(reportRepository.findEmployeeAveragesByRestaurant(restaurantId)).thenReturn(List.of(projection1, projection2));

        List<EmployeeAverageDuration> result = reportJpaAdapter.getEmployeeAverageDurations(restaurantId);

        assertEquals(2, result.size());
        assertEquals(201L, result.get(0).getEmployeeId());
        assertEquals(120L, result.get(0).getAverageDurationSeconds());
        assertEquals(202L, result.get(1).getEmployeeId());
        assertEquals(80L, result.get(1).getAverageDurationSeconds());

        verify(reportRepository).findEmployeeAveragesByRestaurant(restaurantId);
    }
}
