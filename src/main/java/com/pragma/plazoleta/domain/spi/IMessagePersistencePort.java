package com.pragma.plazoleta.domain.spi;

public interface IMessagePersistencePort {
    void sendMessage(String phoneNumber, String pin);
}
