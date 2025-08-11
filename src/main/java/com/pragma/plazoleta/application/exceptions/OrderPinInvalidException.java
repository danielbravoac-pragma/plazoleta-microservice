package com.pragma.plazoleta.application.exceptions;

public class OrderPinInvalidException extends RuntimeException {
    public OrderPinInvalidException(String message) {
        super(message);
    }
}
