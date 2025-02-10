package com.it.patagonia.challenge_it_patagonia.service;

import com.it.patagonia.challenge_it_patagonia.entities.Empresa;
import com.it.patagonia.challenge_it_patagonia.entities.Transferencia;
import com.it.patagonia.challenge_it_patagonia.repository.EmpresaRepository;
import com.it.patagonia.challenge_it_patagonia.repository.TransferenciaRepository;
import com.it.patagonia.challenge_it_patagonia.request.TransferenciaRequest;
import com.it.patagonia.challenge_it_patagonia.response.TransferenciaResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferenciaServiceTest {
    @InjectMocks
    private TransferenciaService transferenciaService;

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private TransferenciaRepository transferenciaRepository;

    @Test
    void realizarTransferencia_CuandoEmpresaExiste_DebeGuardarTransferencia() {

        Empresa empresaMock = new Empresa(1L, 123456789L, "IT Patagonia", LocalDateTime.now());
        TransferenciaRequest request = new TransferenciaRequest(1L, new BigDecimal("1000"), "12345", "67890");

        System.out.println("Mockeando empresaRepository.findById con ID: " + request.getEmpresaId());

        when(empresaRepository.findById(Mockito.eq(1L)))
                .thenReturn(Optional.of(empresaMock));

        when(transferenciaRepository.save(Mockito.any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Optional<TransferenciaResponse> result = transferenciaService.realizarTransferencia(request);

        assertTrue(result.isPresent(), "La transferencia debería haberse guardado");
        assertEquals(new BigDecimal("1000"), result.get().getImporte());
    }

    @Test
    void realizarTransferencia_CuandoEmpresaNoExiste_DebeRetornarVacio() {
        TransferenciaRequest request = new TransferenciaRequest(1L, new BigDecimal("1000"), "12345", "67890");

        when(empresaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<TransferenciaResponse> result = transferenciaService.realizarTransferencia(request);

        assertTrue(result.isEmpty());
    }

    @Test
    void obtenerTransferenciasUltMes_DebeRetornarLista() {
        List<Transferencia> transferenciasMock = List.of(
                new Transferencia(1L, new Empresa(1L, 123456789L, "IT Patagonia", LocalDateTime.now()),
                        new BigDecimal("1000"), "12345", "67890", LocalDateTime.now().minusDays(10))
        );

        when(transferenciaRepository.findTrasfByEmpresasUltimoMes(Mockito.any(LocalDateTime.class)))
                .thenReturn(transferenciasMock);

        List<TransferenciaResponse> result = transferenciaService.obtenerTransferenciasUltMes();

        assertEquals(1, result.size());
        assertEquals(new BigDecimal("1000"), result.get(0).getImporte());
    }
}