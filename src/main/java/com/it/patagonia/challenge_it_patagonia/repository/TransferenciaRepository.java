package com.it.patagonia.challenge_it_patagonia.repository;

import com.it.patagonia.challenge_it_patagonia.entities.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

    @Query("SELECT t FROM Transferencia t WHERE t.fechaTransferencia >= :startDate")
    List<Transferencia> findTrasfByEmpresasUltimoMes(@Param("startDate") LocalDateTime startDate);
}