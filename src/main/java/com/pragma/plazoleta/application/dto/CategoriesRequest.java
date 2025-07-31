package com.pragma.plazoleta.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Solicitud de categorias asociadas a un plato.")
public class CategoriesRequest {
    @NotNull
    @Schema(description = "Id de Categoria asociada", example = "1")
    private Long idCategory;
}
