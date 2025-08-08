package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.model.Status;
import com.pragma.plazoleta.domain.model.StatusEnum;
import com.pragma.plazoleta.domain.spi.IStatusPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StatusUseCaseTest {

    @Mock
    private IStatusPersistencePort statusPersistencePort;

    private StatusUseCase statusUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        statusUseCase = new StatusUseCase(statusPersistencePort);
    }

    @Test
    void findByName_shouldReturnStatus() {
        String name = "PENDING";
        Status expected = new Status();
        expected.setName(Enum.valueOf(StatusEnum.class,name));

        when(statusPersistencePort.findByName(name)).thenReturn(expected);

        Status result = statusUseCase.findByName(name);

        assertEquals(expected, result);
        verify(statusPersistencePort).findByName(name);
    }

    @Test
    void findById_shouldReturnStatus() {
        Long id = 1L;
        Status expected = new Status();
        expected.setId(id);

        when(statusPersistencePort.findById(id)).thenReturn(expected);

        Status result = statusUseCase.findById(id);

        assertEquals(expected, result);
        verify(statusPersistencePort).findById(id);
    }
}
