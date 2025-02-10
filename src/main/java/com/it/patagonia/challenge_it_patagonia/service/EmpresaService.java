package com.it.patagonia.challenge_it_patagonia.service;

import com.it.patagonia.challenge_it_patagonia.entities.Empresa;
import com.it.patagonia.challenge_it_patagonia.repository.EmpresaRepository;
import com.it.patagonia.challenge_it_patagonia.request.EmpresaRequest;
import com.it.patagonia.challenge_it_patagonia.response.EmpresaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public Optional<EmpresaResponse> adherirEmpresa(EmpresaRequest request) {

        if (empresaRepository.existsByCuil(request.getCuil())) {
            return Optional.empty();
        }

        Empresa nuevaEmpresa = Empresa.builder()
                .cuil(request.getCuil())
                .razonSocial(request.getRazonSocial())
                .fechaAdhesion(LocalDateTime.now())
                .build();

        Empresa empresaGuardada = empresaRepository.save(nuevaEmpresa);

        EmpresaResponse response = new EmpresaResponse(
                empresaGuardada.getIdEmpresa(),
                empresaGuardada.getCuil(),
                empresaGuardada.getRazonSocial(),
                empresaGuardada.getFechaAdhesion()
        );

        return Optional.of(response);
    }

    public List<EmpresaResponse> obtenerEmpresasAdheridasUltimoMes() {
        LocalDateTime startDate = LocalDateTime.now().minusMonths(1);
        List<Empresa> empresas = empresaRepository.findEmpresasAdheridasUltimoMes(startDate);

        return empresas.stream()
                .map(emp -> new EmpresaResponse(emp.getIdEmpresa(), emp.getCuil(), emp.getRazonSocial(), emp.getFechaAdhesion()))
                .toList();
    }
}