package com.pragma.plazoleta.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Solicitud de creación de platos.")
public class CreateDishRequest {
    @NotBlank
    @Schema(description = "Nombre del plato", example = "Pizza Americana")
    private String name;

    @NotNull
    @Min(value = 0, message = "Debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 0, message = "Debe ser un número entero")
    @Schema(description = "Precio del plato, entero mayor a 0", example = "35")
    private Integer price;

    @NotBlank
    @Schema(description = "Descripción del plato", example = "Deliciosa Pizza con bordes de queso parmesano.")
    private String description;

    @NotNull
    @Schema(description = "Id de Restaurant a donde estará asociado el plato", example = "1")
    private Long restaurantId;

    @NotBlank
    @Schema(description = "URL de la imagen asociada al plato.", example = "https://photourl.com/image.jpg")
    private String imageUrl;

    @NotNull
    @Schema(description = "Categorías asociadas al plato.")
    private Set<CategoriesRequest> categories;
}
