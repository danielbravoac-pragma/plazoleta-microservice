package com.pragma.plazoleta.application.usecase;

import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.domain.usecase.UserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @InjectMocks
    private UserUseCase userUseCase;

    @Test
    void findById_ShouldReturnUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Daniel");

        when(userPersistencePort.findById(1L)).thenReturn(user);

        User result = userUseCase.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Daniel", result.getName());
        verify(userPersistencePort).findById(1L);
    }
}
