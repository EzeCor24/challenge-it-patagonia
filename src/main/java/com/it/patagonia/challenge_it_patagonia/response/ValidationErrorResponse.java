package com.it.patagonia.challenge_it_patagonia.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ValidationErrorResponse", description = "Formato de error de validación")
public class ValidationErrorResponse  {
    @Schema(description = "Mapa de errores de validación", example = "{\"cuil\": \"must not be null\"}")
    private Map<String, String> errors;
}