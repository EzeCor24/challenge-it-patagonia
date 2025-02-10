package com.it.patagonia.challenge_it_patagonia.controller;

import com.it.patagonia.challenge_it_patagonia.request.TransferenciaRequest;
import com.it.patagonia.challenge_it_patagonia.response.TransferenciaResponse;
import com.it.patagonia.challenge_it_patagonia.service.TransferenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transferencias")
@RequiredArgsConstructor
@Slf4j
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    @Operation(
            summary ="Retorna las empresas que hicieron transferencias el último mes.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Si no hay empresas con transferencias en el último mes"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            })
    @GetMapping("/get-trasferencias-ult-mes")
    public ResponseEntity<List<TransferenciaResponse>> getTrasferenciasUltMes(){
        try {
            var transferencias = transferenciaService.obtenerTransferenciasUltMes();
            if (transferencias.isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(transferencias);

        } catch (DataAccessException e) {
            log.error("Error en la base de datos al buscar las empresas que hicieron transferencias el último mes.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception e) {
            log.error("Error inesperado al buscar las empresas que hicieron transferencias el último mes.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary ="Realizar una transferencia",
            description = "Guarda una nueva transferencia en la base de datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la transferencia a registrar",
                    required = true
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Transferencia realizada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            })
    @PostMapping("/realizar-transferencia")
    public ResponseEntity<?> realizarTransferencia(@Valid @RequestBody TransferenciaRequest request) {
        log.info("Request: {}", request);

        try {
            var transferencia = transferenciaService.realizarTransferencia(request);

            if (transferencia.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empresa no encontrada");
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(transferencia);

        } catch (DataAccessException e) {
            log.error("Error en base de datos al procesar transferencia", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la base de datos");
        } catch (Exception e) {
            log.error("Error inesperado al procesar transferencia", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
        }
    }
}