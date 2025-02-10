package com.it.patagonia.challenge_it_patagonia.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Empresa {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idEmpresa;

    @Column(nullable = false, unique = true)
    private Long cuil;

    @Column(nullable = false)
    private String razonSocial;
    private LocalDateTime fechaAdhesion;
}