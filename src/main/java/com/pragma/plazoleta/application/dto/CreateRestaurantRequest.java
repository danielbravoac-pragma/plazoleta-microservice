package com.pragma.plazoleta.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Solicitud de creación de restaurante asociado al usuario.")
public class CreateRestaurantRequest {
    @NotBlank
    @Pattern(regexp = "^(?!\\d+$)[\\p{L}0-9 .,'-]+$", message = "El nombre no puede ser solo números y debe contener letras o letras+números")
    @Schema(description = "Nombre del restaurante", example = "Pizzeria Trattoria")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[0-9]{5,15}(-[0-9])?$", message = "El NIT debe tener entre 5 y 15 dígitos y un dígito de verificación opcional")
    @Schema(description = "NIT del Comercio", example = "123456789632587-8")
    private String nit;

    @NotBlank
    @Schema(description = "Direccion del restaurante", example = "Calle Salame y Queso Fresco 123")
    private String address;

    @NotBlank
    @Size(min = 10, max = 13)
    @Pattern(regexp = "^\\+\\d{9,12}$", message = "El formato del teléfono no es válido. Debe empezar con '+' seguido de 9 a 12 dígitos (ej: +570000000000).")
    @Schema(description = "Número de teléfono asociado al restaurante", example = "+579638527412")
    private String phoneNumber;

    @NotBlank
    @Schema(description = "URL de la imagen asociada al restaurante", example = "https://photourl.com/image.jpg")
    private String logoUrl;
}
