package com.it.patagonia.challenge_it_patagonia.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Transferencia {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idTrasferencia;

    @ManyToOne
    @JoinColumn(name = "idEmpresa", nullable = false)
    private Empresa empresa;

    @Column(nullable = false)
    private BigDecimal importe;

    @Column(nullable = false, length = 30)
    private String cuentaDebito;

    @Column(nullable = false, length = 30)
    private String cuentaCredito;

    @Column(nullable = false)
    private LocalDateTime fechaTransferencia;
}