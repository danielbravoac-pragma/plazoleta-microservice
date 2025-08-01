package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.CreateDishRequest;
import com.pragma.plazoleta.application.dto.CreateDishResponse;
import com.pragma.plazoleta.application.dto.UpdateDishRequest;
import com.pragma.plazoleta.application.dto.UpdateDishResponse;
import com.pragma.plazoleta.application.handler.IDishHandler;
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

@RestController
@RequestMapping(value = "/dishes", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Dish", description = "Endpoints de manejo de platos del restaurante.")
public class DishRestController {

    private final IDishHandler dishHandler;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    @Operation(
            summary = "Crear Plato",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Realiza la creación del plato asociado a un restaurante donde el usuario es propietario.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Plato creado con éxito",
                            content = @Content(schema = @Schema(implementation = CreateDishResponse.class))),
                    @ApiResponse(responseCode = "401", description = "El Usuario no es Propietario o no puede crear platos",
                            content = @Content(schema = @Schema(implementation = Info.class))),
                    @ApiResponse(responseCode = "400", description = "Parámetros inválidos",
                            content = @Content(schema = @Schema(implementation = Info.class)))
            }
    )
    public ResponseEntity<CreateDishResponse> saveDish(@RequestBody @Valid CreateDishRequest createDishRequest) {
        return new ResponseEntity<>(dishHandler.saveDish(createDishRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping
    @Operation(
            summary = "Actualizar Plato",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Realiza la actualización de un plato asociado a un restaurante donde el usuario es propietario.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Plato actualizado con éxito",
                            content = @Content(schema = @Schema(implementation = UpdateDishResponse.class))),
                    @ApiResponse(responseCode = "401", description = "El Usuario no es Propietario o no puede actualizar platos",
                            content = @Content(schema = @Schema(implementation = Info.class))),
                    @ApiResponse(responseCode = "404", description = "Plato no encontrado para actualizar",
                            content = @Content(schema = @Schema(implementation = Info.class))),
                    @ApiResponse(responseCode = "400", description = "Parámetros inválidos",
                            content = @Content(schema = @Schema(implementation = Info.class)))
            }
    )
    public ResponseEntity<UpdateDishResponse> updateDish(@RequestBody @Valid UpdateDishRequest updateDishRequest) {
        return new ResponseEntity<>(dishHandler.updateDish(updateDishRequest), HttpStatus.OK);
    }
}
