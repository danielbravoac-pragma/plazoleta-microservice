package com.pragma.plazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderDetailTraceabilityResponse {
    private String status;
    private LocalDateTime assignedDate;
}
