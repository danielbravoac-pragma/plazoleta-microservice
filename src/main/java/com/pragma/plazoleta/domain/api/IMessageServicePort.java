package com.pragma.plazoleta.domain.api;

public interface IMessageServicePort {
    void sendMessage(String phoneNumber, String pin);
}
