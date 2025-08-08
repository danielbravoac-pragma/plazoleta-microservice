package com.pragma.plazoleta.infrastructure.output.feign.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "messages", url = "${messages.url}")
public interface MessageClient {
    @PostMapping("/messages/send-pin")
    void sendMessage(@RequestParam(name = "phoneNumber") String phoneNumber,
                     @RequestParam(name = "pin") String pin);
}
