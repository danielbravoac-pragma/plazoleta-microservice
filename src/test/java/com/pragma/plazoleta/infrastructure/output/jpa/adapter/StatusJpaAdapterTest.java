package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Status;
import com.pragma.plazoleta.infrastructure.exception.DataNotFoundException;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.StatusEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IStatusEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatusJpaAdapterTest {

    @Mock
    private IStatusRepository statusRepository;

    @Mock
    private IStatusEntityMapper statusEntityMapper;

    private StatusJpaAdapter statusJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        statusJpaAdapter = new StatusJpaAdapter(statusRepository, statusEntityMapper);
    }

    @Test
    void findByName_shouldReturnMappedStatus() {
        String name = "PENDING";
        StatusEntity entity = new StatusEntity();
        Status status = new Status();

        when(statusRepository.findByName(name)).thenReturn(entity);
        when(statusEntityMapper.toStatus(entity)).thenReturn(status);

        Status result = statusJpaAdapter.findByName(name);

        assertEquals(status, result);
        verify(statusRepository).findByName(name);
        verify(statusEntityMapper).toStatus(entity);
    }

    @Test
    void findById_shouldReturnMappedStatus_whenFound() {
        Long id = 1L;
        StatusEntity entity = new StatusEntity();
        Status status = new Status();

        when(statusRepository.findById(id)).thenReturn(Optional.of(entity));
        when(statusEntityMapper.toStatus(entity)).thenReturn(status);

        Status result = statusJpaAdapter.findById(id);

        assertEquals(status, result);
        verify(statusRepository).findById(id);
        verify(statusEntityMapper).toStatus(entity);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        Long id = 2L;
        when(statusRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> statusJpaAdapter.findById(id));
        verify(statusRepository).findById(id);
        verify(statusEntityMapper, never()).toStatus(any());
    }
}
