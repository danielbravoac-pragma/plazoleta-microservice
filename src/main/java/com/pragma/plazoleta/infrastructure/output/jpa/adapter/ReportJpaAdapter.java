package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.EmployeeAverageDuration;
import com.pragma.plazoleta.domain.model.OrderDuration;
import com.pragma.plazoleta.domain.spi.IReportPersistencePort;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IReportRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReportJpaAdapter implements IReportPersistencePort {
    private final IReportRepository reportRepository;

    @Override
    public List<OrderDuration> getOrderDurations(Long restaurantId) {
        return reportRepository.findOrderDurationsByRestaurant(restaurantId).stream()
                .map(p -> new OrderDuration(p.getOrderId(), p.getDurationSeconds()))
                .toList();
    }

    @Override
    public List<EmployeeAverageDuration> getEmployeeAverageDurations(Long restaurantId) {
        return reportRepository.findEmployeeAveragesByRestaurant(restaurantId).stream()
                .map(p -> new EmployeeAverageDuration(p.getEmployeeId(), p.getAverageDurationSeconds()))
                .toList();
    }

}
