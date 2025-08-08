package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.model.EmployeeAverageDuration;
import com.pragma.plazoleta.domain.model.OrderDuration;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IReportPersistencePort;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReportUseCaseTest {

    @Mock
    private IReportPersistencePort reportPersistencePort;

    @Mock
    private IUserServicePort userServicePort;

    private ReportUseCase reportUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reportUseCase = new ReportUseCase(reportPersistencePort, userServicePort);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getOrderDurations_shouldReturnListBasedOnLoggedUserRestaurant() {
        Long userId = 5L;
        Long restaurantId = 10L;
        User user = new User();
        user.setId(userId);
        user.setRestaurantId(restaurantId);

        List<OrderDuration> expected = List.of(new OrderDuration(), new OrderDuration());

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(String.valueOf(userId), null)
        );

        when(userServicePort.findById(userId)).thenReturn(user);
        when(reportPersistencePort.getOrderDurations(restaurantId)).thenReturn(expected);

        List<OrderDuration> result = reportUseCase.getOrderDurations();

        assertEquals(expected, result);
        verify(userServicePort).findById(userId);
        verify(reportPersistencePort).getOrderDurations(restaurantId);
    }

    @Test
    void getEmployeeAverageDurations_shouldReturnListBasedOnLoggedUserRestaurant() {
        Long userId = 7L;
        Long restaurantId = 20L;
        User user = new User();
        user.setId(userId);
        user.setRestaurantId(restaurantId);

        List<EmployeeAverageDuration> expected = List.of(new EmployeeAverageDuration());

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(String.valueOf(userId), null)
        );

        when(userServicePort.findById(userId)).thenReturn(user);
        when(reportPersistencePort.getEmployeeAverageDurations(restaurantId)).thenReturn(expected);

        List<EmployeeAverageDuration> result = reportUseCase.getEmployeeAverageDurations();

        assertEquals(expected, result);
        verify(userServicePort).findById(userId);
        verify(reportPersistencePort).getEmployeeAverageDurations(restaurantId);
    }
}
