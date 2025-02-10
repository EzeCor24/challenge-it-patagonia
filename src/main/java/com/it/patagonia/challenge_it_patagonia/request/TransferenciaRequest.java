package com.it.patagonia.challenge_it_patagonia.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(name = "TransferenciaRequest")
public class TransferenciaRequest {
    @Schema(name = "empresaId", description = "Id de la empresa", example = "1")
    @NotNull(message = "El ID de la empresa es obligatorio")
    private Long empresaId;

    @Schema(name = "importe", description = "importe de transferencia", example = "100")
    @NotNull(message = "El importe es obligatorio")
    @DecimalMin(value = "0.01", message = "El importe debe ser mayor a 0")
    private BigDecimal importe;

    @Schema(name = "cuentaDebito", example = "Debito 1")
    @NotBlank(message = "La cuenta de débito es obligatoria")
    private String cuentaDebito;

    @Schema(name = "cuentaCredito", example = "Credito 1")
    @NotBlank(message = "La cuenta de crédito es obligatoria")
    private String cuentaCredito;
}