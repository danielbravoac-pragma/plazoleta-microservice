package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.response.EmployeeAverageDurationResponse;
import com.pragma.plazoleta.application.dto.response.GetOrderDurationsResponse;
import com.pragma.plazoleta.application.handler.IReportHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportRestController {

    private final IReportHandler reportHandler;

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/order-durations")
    public ResponseEntity<List<GetOrderDurationsResponse>> getOrderDurationsResponseResponseEntity() {
        return new ResponseEntity<>(reportHandler.getOrderDurationsResponseList(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/employee-average-duration")
    public ResponseEntity<List<EmployeeAverageDurationResponse>> getEmployeeAverageDuration() {
        return new ResponseEntity<>(reportHandler.getEmployeeAverageDurationResponse(), HttpStatus.OK);
    }
}
