package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.CreateOrderRequest;
import com.pragma.plazoleta.application.dto.response.CreateOrderResponse;
import com.pragma.plazoleta.application.dto.response.FindOrderResponse;
import com.pragma.plazoleta.application.dto.response.PageResponse;
import com.pragma.plazoleta.application.handler.IOrderHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final IOrderHandler orderHandler;

    @PostMapping("/create")
    public ResponseEntity<CreateOrderResponse> saveOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        return new ResponseEntity<>(orderHandler.saveOrder(createOrderRequest), HttpStatus.CREATED);
    }


    @PreAuthorize("hasAnyRole('OWNER','EMPLOYEE')")
    @GetMapping
    public ResponseEntity<PageResponse<FindOrderResponse>> findOrderResponse(@RequestParam(name = "statusId") Long statusId,
                                                                             @RequestParam(name = "page") Integer page,
                                                                             @RequestParam(name = "size") Integer size) {
        return new ResponseEntity<>(orderHandler.findOrdersWithLatestStatus(statusId, page, size), HttpStatus.OK);
    }
}
