package com.pragma.plazoleta.infrastructure.output.feign.exception;

import com.pragma.plazoleta.infrastructure.exception.DataNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        int status = response.status();

        if (status == 404) {
            return new DataNotFoundException("The user does not exist.");
        }

        if (status >= 400 && status < 500) {
            return new IllegalArgumentException("Error client in external service " + status);
        }

        if (status >= 500) {
            return new RuntimeException("Internal error external service" + status);
        }
        return new Exception("Unknown error service in Feign call");
    }
}
