package com.it.patagonia.challenge_it_patagonia.service;

import com.it.patagonia.challenge_it_patagonia.entities.Empresa;
import com.it.patagonia.challenge_it_patagonia.entities.Transferencia;
import com.it.patagonia.challenge_it_patagonia.repository.EmpresaRepository;
import com.it.patagonia.challenge_it_patagonia.repository.TransferenciaRepository;
import com.it.patagonia.challenge_it_patagonia.request.TransferenciaRequest;
import com.it.patagonia.challenge_it_patagonia.response.TransferenciaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferenciaService {
    private final EmpresaRepository empresaRepository;
    private final TransferenciaRepository transferenciaRepository;

    public Optional<TransferenciaResponse> realizarTransferencia(TransferenciaRequest request) {
        Optional<Empresa> empresaOpt = empresaRepository.findById(request.getEmpresaId());

        if (empresaOpt.isEmpty()) {
            log.warn("Empresa con ID {} no encontrada", request.getEmpresaId());
            return Optional.empty();
        }

        Transferencia transferencia = Transferencia.builder()
                .empresa(empresaOpt.get())
                .importe(request.getImporte())
                .cuentaDebito(request.getCuentaDebito())
                .cuentaCredito(request.getCuentaCredito())
                .fechaTransferencia(LocalDateTime.now())
                .build();

        Transferencia transferenciaGuardada = transferenciaRepository.save(transferencia);

        return Optional.of(new TransferenciaResponse(
                transferenciaGuardada.getIdTrasferencia(), transferenciaGuardada.getEmpresa().getIdEmpresa(),
                transferenciaGuardada.getImporte(), transferenciaGuardada.getCuentaDebito(),
                transferenciaGuardada.getCuentaCredito(), transferenciaGuardada.getFechaTransferencia()));
    }

    public List<TransferenciaResponse> obtenerTransferenciasUltMes() {
        LocalDateTime startDate = LocalDateTime.now().minusMonths(1);
        List<Transferencia> transferencias = transferenciaRepository.findTrasfByEmpresasUltimoMes(startDate);

        return transferencias.stream()
                .map(transf -> new TransferenciaResponse(transf.getIdTrasferencia(), transf.getEmpresa().getIdEmpresa(), transf.getImporte(),
                        transf.getCuentaDebito(), transf.getCuentaCredito(), transf.getFechaTransferencia())).toList();
    }
}