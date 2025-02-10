package com.it.patagonia.challenge_it_patagonia.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaResponse {
    @Schema(name = "idTrasferencia", description = "ID de la transferencia")
    private Long idTrasferencia;
    @Schema(name = "empresaId", description = "ID de la empresa")
    private Long empresaId;
    @Schema(name = "importe", description = "Importe de la transferencia")
    private BigDecimal importe;
    @Schema(name = "cuentaDebito", description = "Cuenta Debito de la transferencia")
    private String cuentaDebito;
    @Schema(name = "cuentaCredito", description = "Cuenta Credito de la transferencia")
    private String cuentaCredito;
    @Schema(name = "fechaTransferencia", description = "Fecha de la transferencia")
    private LocalDateTime fechaTransferencia;
}