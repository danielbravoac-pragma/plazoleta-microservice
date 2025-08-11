package com.pragma.plazoleta.infrastructure.output.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FeignAuthInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String token = SecurityContextHolder.getContext().getAuthentication() != null
                ? (String) SecurityContextHolder.getContext().getAuthentication().getCredentials()
                : null;

        if (token != null) {
            template.header("Authorization", "Bearer " + token);
        }
    }
}
