package com.it.patagonia.challenge_it_patagonia.repository;

import com.it.patagonia.challenge_it_patagonia.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{
    boolean existsByCuil(Long cuil);

    @Query("SELECT e FROM Empresa e WHERE e.fechaAdhesion >= :startDate")
    List<Empresa> findEmpresasAdheridasUltimoMes(@Param("startDate") LocalDateTime startDate);
}
