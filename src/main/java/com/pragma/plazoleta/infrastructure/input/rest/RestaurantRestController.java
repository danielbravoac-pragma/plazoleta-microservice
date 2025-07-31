package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.CreateRestaurantRequest;
import com.pragma.plazoleta.application.dto.CreateRestaurantResponse;
import com.pragma.plazoleta.application.handler.IRestaurantHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    @Operation(
            summary = "Crear Restaurante",
            description = "Realiza la creación de un restaurante asociado al propietario.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Restaurante creado con éxito",
                            content = @Content(schema = @Schema(implementation = CreateRestaurantResponse.class))),
                    @ApiResponse(responseCode = "401", description = "El Usuario no puede crear Restaurantes"),
                    @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
            }
    )
    public ResponseEntity<CreateRestaurantResponse> saveRestaurant(@Valid @RequestBody CreateRestaurantRequest createRestaurantRequest) {
        return new ResponseEntity<>(restaurantHandler.saveRestaurant(createRestaurantRequest), HttpStatus.CREATED);
    }
}
