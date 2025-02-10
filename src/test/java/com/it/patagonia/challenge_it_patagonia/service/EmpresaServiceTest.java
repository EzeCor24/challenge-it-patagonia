package com.it.patagonia.challenge_it_patagonia.service;

import com.it.patagonia.challenge_it_patagonia.entities.Empresa;
import com.it.patagonia.challenge_it_patagonia.repository.EmpresaRepository;
import com.it.patagonia.challenge_it_patagonia.request.EmpresaRequest;
import com.it.patagonia.challenge_it_patagonia.response.EmpresaResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmpresaServiceTest {
    @InjectMocks
    private EmpresaService empresaService;

    @Mock
    private EmpresaRepository empresaRepository;

    @Test
    void adherirEmpresa_CuandoNoExiste_DebeGuardarla() {
        EmpresaRequest request = new EmpresaRequest(123456789L, "IT Patagonia");
        Empresa empresaMock = new Empresa(1L, request.getCuil(), request.getRazonSocial(), LocalDateTime.now());

        when(empresaRepository.existsByCuil(request.getCuil())).thenReturn(false);
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresaMock);

        Optional<EmpresaResponse> result = empresaService.adherirEmpresa(request);

        assertTrue(result.isPresent());
        assertEquals(request.getCuil(), result.get().getCuil());
        assertEquals(request.getRazonSocial(), result.get().getRazonSocial());
    }

    @Test
    void adherirEmpresa_CuandoYaExiste_DebeRetornarVacio() {
        EmpresaRequest request = new EmpresaRequest(123456789L, "IT Patagonia");

        when(empresaRepository.existsByCuil(request.getCuil())).thenReturn(true);

        Optional<EmpresaResponse> result = empresaService.adherirEmpresa(request);

        assertTrue(result.isEmpty());
    }

    @Test
    void obtenerEmpresasAdheridasUltimoMes_DebeRetornarEmpresas() {
        List<Empresa> empresasMock = List.of(
                new Empresa(1L, 123456789L, "IT Patagonia", LocalDateTime.now().minusDays(15)),
                new Empresa(2L, 987654321L, "Globant", LocalDateTime.now().minusDays(20))
        );

        Mockito.when(empresaRepository.findEmpresasAdheridasUltimoMes(Mockito.any(LocalDateTime.class)))
                .thenReturn(empresasMock);

        List<EmpresaResponse> result = empresaService.obtenerEmpresasAdheridasUltimoMes();

        assertEquals(2, result.size());
        assertEquals("IT Patagonia", result.get(0).getRazonSocial());
        assertEquals("Globant", result.get(1).getRazonSocial());
    }
}