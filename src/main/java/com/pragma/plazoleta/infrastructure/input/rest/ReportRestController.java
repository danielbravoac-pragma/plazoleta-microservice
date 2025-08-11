package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.response.CreateRestaurantResponse;
import com.pragma.plazoleta.application.dto.response.EmployeeAverageDurationResponse;
import com.pragma.plazoleta.application.dto.response.GetOrderDurationsResponse;
import com.pragma.plazoleta.application.handler.IReportHandler;
import com.pragma.plazoleta.infrastructure.exceptionhandler.Info;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/report",produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Report", description = "Reportes de eficiencia de ordenes.")
public class ReportRestController {

    private final IReportHandler reportHandler;

    @PreAuthorize("hasRole('OWNER')")
    @Operation(
            summary = "Obtener duración de cada orden",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Obtener duración de cada orden desde status PENDIENTE a ENTREGADO.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reporte expedido correctamente",
                            content = @Content(schema = @Schema(implementation = GetOrderDurationsResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error Interno")
            }
    )
    @GetMapping("/order-durations")
    public ResponseEntity<List<GetOrderDurationsResponse>> getOrderDurationsResponseResponseEntity() {
        return new ResponseEntity<>(reportHandler.getOrderDurationsResponseList(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('OWNER')")
    @Operation(
            summary = "Obtener promedio de tiempo por cada orden",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Obtener promedio de tiempo de atención de cada pedido del empleado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reporte expedido correctamente",
                            content = @Content(schema = @Schema(implementation = GetOrderDurationsResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error Interno")
            }
    )
    @GetMapping("/employee-average-duration")
    public ResponseEntity<List<EmployeeAverageDurationResponse>> getEmployeeAverageDuration() {
        return new ResponseEntity<>(reportHandler.getEmployeeAverageDurationResponse(), HttpStatus.OK);
    }
}
