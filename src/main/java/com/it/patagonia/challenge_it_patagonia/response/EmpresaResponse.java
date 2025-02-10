package com.it.patagonia.challenge_it_patagonia.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta después de adherir una empresa")
public class EmpresaResponse {
    @Schema(name = "idEmpresa", description = "ID de la empresa")
    private long idEmpresa;

    @Schema(name = "cuil", description = "Número de cuil")
    private Long cuil;

    @Schema(name = "razonSocial", description = "Nombre de la empresa")
    private String razonSocial;

    @Schema(name = "fechaAdhesion", description = "Fecha de adhesión", example = "2024-02-09T15:30:00")
    private LocalDateTime fechaAdhesion;
}