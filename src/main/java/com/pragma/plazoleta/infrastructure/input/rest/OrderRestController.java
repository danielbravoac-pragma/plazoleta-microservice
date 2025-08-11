package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.CreateOrderRequest;
import com.pragma.plazoleta.application.dto.response.*;
import com.pragma.plazoleta.application.handler.IOrderHandler;
import com.pragma.plazoleta.infrastructure.exceptionhandler.Info;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Gestión de pedidos(ordenes) de cada restaurante.")
public class OrderRestController {

    private final IOrderHandler orderHandler;

    @Operation(
            summary = "Crear Pedido",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Realiza la creación de un pedido asociado al restaurante.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pedido creado con éxito",
                            content = @Content(schema = @Schema(implementation = CreateRestaurantResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Parámetros inválidos",
                            content = @Content(schema = @Schema(implementation = Info.class))),
                    @ApiResponse(responseCode = "500", description = "Error Interno")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<CreateOrderResponse> saveOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        return new ResponseEntity<>(orderHandler.saveOrder(createOrderRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('OWNER','EMPLOYEE')")
    @Operation(
            summary = "Actualizar Status de Pedido a En Progreso",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Realiza la actualización del estado En Progreso del Pedido y asigna al empleado que atiende el pedido.",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Status actualizado con éxito",
                            content = @Content(schema = @Schema(implementation = CreateRestaurantResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Pedido en progreso, no puede actualizar.",
                            content = @Content(schema = @Schema(implementation = Info.class))),
                    @ApiResponse(responseCode = "500", description = "Error Interno")
            }
    )
    @PutMapping("/in-progress")
    public ResponseEntity<UpdateStatusOrderResponse> putInProgress(@RequestParam(name = "idOrder") Long idOrder) {
        return new ResponseEntity<>(orderHandler.assignEmployeeAndPutInProgress(idOrder), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('OWNER','EMPLOYEE')")
    @Operation(
            summary = "Actualizar Status de Pedido a Hecho",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Realiza la actualización del estado Hecho del Pedido.",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Status actualizado con éxito",
                            content = @Content(schema = @Schema(implementation = CreateRestaurantResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Pedido en progreso, no puede actualizar.",
                            content = @Content(schema = @Schema(implementation = Info.class))),
                    @ApiResponse(responseCode = "500", description = "Error Interno")
            }
    )
    @PutMapping("/done")
    public ResponseEntity<UpdateStatusOrderResponse> putDone(@RequestParam(name = "idOrder") Long idOrder) {
        return new ResponseEntity<>(orderHandler.setDone(idOrder), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('OWNER','EMPLOYEE')")
    @Operation(
            summary = "Actualizar Status de Pedido a Entregado",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Realiza la actualización del estado Entregado del Pedido.",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Status actualizado con éxito",
                            content = @Content(schema = @Schema(implementation = CreateRestaurantResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Pedido en progreso, no puede actualizar.",
                            content = @Content(schema = @Schema(implementation = Info.class))),
                    @ApiResponse(responseCode = "401", description = "PIN Inválido.",
                            content = @Content(schema = @Schema(implementation = Info.class))),
                    @ApiResponse(responseCode = "500", description = "Error Interno")
            }
    )
    @PutMapping("/deliver")
    public ResponseEntity<UpdateStatusOrderResponse> putDelivered(@RequestParam(name = "idOrder") Long idOrder,
                                                                  @RequestParam(name = "pin") String pin) {
        return new ResponseEntity<>(orderHandler.deliveredOrder(idOrder, pin), HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Actualizar Status de Pedido a Cancelado",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Realiza la actualización del estado Cancelado del Pedido.",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Status actualizado con éxito",
                            content = @Content(schema = @Schema(implementation = CreateRestaurantResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Pedido en progreso, no puede actualizar.",
                            content = @Content(schema = @Schema(implementation = Info.class))),
                    @ApiResponse(responseCode = "500", description = "Error Interno")
            }
    )
    @PutMapping("/cancel")
    public ResponseEntity<UpdateStatusOrderResponse> putCancelled(@RequestParam(name = "idOrder") Long idOrder) {
        return new ResponseEntity<>(orderHandler.cancelOrder(idOrder), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('OWNER','EMPLOYEE')")
    @Operation(
            summary = "Filtrar ordenes por status",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Realiza el filtrado de ordenes por status paginado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ordenes listadas",
                            content = @Content(schema = @Schema(implementation = CreateRestaurantResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Parametros inválidos.",
                            content = @Content(schema = @Schema(implementation = Info.class))),
                    @ApiResponse(responseCode = "500", description = "Error Interno")
            }
    )
    @GetMapping
    public ResponseEntity<PageResponse<FindOrderResponse>> findOrderResponse(@RequestParam(name = "statusId", defaultValue = "2", required = true) Long statusId,
                                                                             @RequestParam(name = "page", defaultValue = "0", required = true) Integer page,
                                                                             @RequestParam(name = "size", defaultValue = "10", required = true) Integer size) {
        return new ResponseEntity<>(orderHandler.findOrdersWithLatestStatus(statusId, page, size), HttpStatus.OK);
    }

    @GetMapping("/trace")
    @Operation(
            summary = "Obtener detalle de status de orden",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Realiza el listado de status de una orden.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status listado",
                            content = @Content(schema = @Schema(implementation = CreateRestaurantResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Parametros inválidos.",
                            content = @Content(schema = @Schema(implementation = Info.class))),
                    @ApiResponse(responseCode = "500", description = "Error Interno")
            }
    )
    public ResponseEntity<List<GetOrderDetailTraceabilityResponse>> getTraceability(@RequestParam(name = "idOrder", required = true) Long idOrder) {
        return new ResponseEntity<>(orderHandler.getDetailOrder(idOrder), HttpStatus.OK);
    }


}
