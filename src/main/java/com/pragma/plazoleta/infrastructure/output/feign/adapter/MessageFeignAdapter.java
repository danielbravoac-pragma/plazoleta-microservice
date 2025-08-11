package com.pragma.plazoleta.infrastructure.output.feign.adapter;

import com.pragma.plazoleta.domain.spi.IMessagePersistencePort;
import com.pragma.plazoleta.infrastructure.output.feign.client.MessageClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageFeignAdapter implements IMessagePersistencePort {

    private final MessageClient messageClient;

    @Override
    public void sendMessage(String phoneNumber, String pin) {
        messageClient.sendMessage(phoneNumber, pin);
    }
}
