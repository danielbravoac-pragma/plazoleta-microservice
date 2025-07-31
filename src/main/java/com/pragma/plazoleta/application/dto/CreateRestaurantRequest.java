package com.pragma.plazoleta.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRestaurantRequest {
    @NotBlank
    @Pattern(regexp = "^(?!\\d+$)[\\p{L}0-9 ]+$", message = "El nombre no puede ser solo números y debe contener letras o letras+números")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[0-9]{5,15}(-[0-9])?$", message = "El NIT debe tener entre 5 y 15 dígitos y un dígito de verificación opcional")
    private String nit;

    @NotBlank
    private String address;

    @NotBlank
    @Size(min = 10, max = 13)
    @Pattern(regexp = "^\\+\\d{9,12}$", message = "El formato del teléfono no es válido. Debe empezar con '+' seguido de 9 a 12 dígitos (ej: +570000000000).")
    private String phoneNumber;

    @NotBlank
    private String logoUrl;
}
