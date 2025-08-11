package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.spi.IMessagePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

class MessageUseCaseTest {

    @Mock
    private IMessagePersistencePort messagePersistencePort;

    private MessageUseCase messageUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageUseCase = new MessageUseCase(messagePersistencePort);
    }

    @Test
    void testSendMessage_DelegatesToPersistencePort() {
        String phoneNumber = "+51987654321";
        String pin = "1234";

        messageUseCase.sendMessage(phoneNumber, pin);

        verify(messagePersistencePort).sendMessage(phoneNumber, pin);
    }
}
