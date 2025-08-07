package com.pragma.plazoleta.application.exceptions;

public class OrderInProgressException extends RuntimeException{
    public OrderInProgressException(String message) {
        super(message);
    }
}
