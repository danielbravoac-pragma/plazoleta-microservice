package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IReportServicePort;
import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.model.EmployeeAverageDuration;
import com.pragma.plazoleta.domain.model.OrderDuration;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IReportPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RequiredArgsConstructor
public class ReportUseCase implements IReportServicePort {

    private final IReportPersistencePort reportPersistencePort;
    private final IUserServicePort userServicePort;

    @Override
    public List<OrderDuration> getOrderDurations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long loggedUserId = Long.valueOf((String) auth.getPrincipal());
        User user = userServicePort.findById(loggedUserId);
        return reportPersistencePort.getOrderDurations(user.getRestaurantId());
    }

    @Override
    public List<EmployeeAverageDuration> getEmployeeAverageDurations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long loggedUserId = Long.valueOf((String) auth.getPrincipal());
        User user = userServicePort.findById(loggedUserId);
        return reportPersistencePort.getEmployeeAverageDurations(user.getRestaurantId());
    }
}
