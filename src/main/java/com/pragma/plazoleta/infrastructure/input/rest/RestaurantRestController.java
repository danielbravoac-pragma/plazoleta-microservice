package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.CreateRestaurantRequest;
import com.pragma.plazoleta.application.dto.response.CreateRestaurantResponse;
import com.pragma.plazoleta.application.dto.response.PageResponse;
import com.pragma.plazoleta.application.dto.response.PageRestaurantResponse;
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
import org.springframework.web.bind.annotation.*;

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
                    @ApiResponse(responseCode = "201", description = "Restaurante creado con éxito",
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


    @Operation(
            summary = "Listar todos los restaurantes",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Realiza el listado de restaurantes disponibles.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de restaurantes.",
                            content = @Content(schema = @Schema(implementation = PageResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Parámetros inválidos",
                            content = @Content(schema = @Schema(implementation = Info.class))),
                    @ApiResponse(responseCode = "500", description = "Error Interno")
            }
    )
    @GetMapping
    public ResponseEntity<PageResponse<PageRestaurantResponse>> findAllRestaurants(@RequestParam(name = "page", required = true, value = "0") Integer page,
                                                                                   @RequestParam(name = "size", required = true, value = "5") Integer size) {
        return new ResponseEntity<>(restaurantHandler.findAll(page, size), HttpStatus.OK);
    }
}
