package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.CreateRestaurantRequest;
import com.pragma.plazoleta.application.dto.CreateRestaurantResponse;
import com.pragma.plazoleta.application.handler.IRestaurantHandler;
import com.pragma.plazoleta.infrastructure.exceptionhandler.Info;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/restaurants", produces = "application/json")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    @Operation(
            summary = "Crear Restaurante",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Realiza la creación de un restaurante asociado al propietario.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Restaurante creado con éxito",
                            content = @Content(schema = @Schema(implementation = CreateRestaurantResponse.class))),
                    @ApiResponse(responseCode = "401", description = "El Usuario no puede crear Restaurantes",
                            content = @Content(schema = @Schema(implementation = Info.class))),
                    @ApiResponse(responseCode = "400", description = "Parámetros inválidos",
                            content = @Content(schema = @Schema(implementation = Info.class)))
            }
    )
    public ResponseEntity<CreateRestaurantResponse> saveRestaurant(@Valid @RequestBody CreateRestaurantRequest createRestaurantRequest) {
        return new ResponseEntity<>(restaurantHandler.saveRestaurant(createRestaurantRequest), HttpStatus.CREATED);
    }
}
