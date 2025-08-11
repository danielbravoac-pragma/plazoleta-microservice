package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IMessageServicePort;
import com.pragma.plazoleta.domain.spi.IMessagePersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageUseCase implements IMessageServicePort {

    private final IMessagePersistencePort messagePersistencePort;

    @Override
    public void sendMessage(String phoneNumber, String pin) {
        messagePersistencePort.sendMessage(phoneNumber, pin);
    }
}
