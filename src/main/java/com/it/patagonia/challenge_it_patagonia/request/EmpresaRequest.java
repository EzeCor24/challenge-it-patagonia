package com.it.patagonia.challenge_it_patagonia.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(name = "EmpresaRequest")
public class EmpresaRequest {
    @Schema(name = "cuil", description = "Número de cuil", example = "20232323234")
    @NotNull(message = "El número de cuil es obligatorio")
    private Long cuil;

    @Schema(name = "razonSocial", description = "Nombre de la empresa", example = "It-Patagonia")
    @NotBlank(message = "La razonSocial es obligatoria")
    private String razonSocial;
}