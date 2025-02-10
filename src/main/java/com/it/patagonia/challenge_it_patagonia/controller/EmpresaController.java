package com.it.patagonia.challenge_it_patagonia.controller;

import com.it.patagonia.challenge_it_patagonia.request.EmpresaRequest;
import com.it.patagonia.challenge_it_patagonia.response.EmpresaResponse;
import com.it.patagonia.challenge_it_patagonia.response.ResponseDefault;
import com.it.patagonia.challenge_it_patagonia.response.ValidationErrorResponse;
import com.it.patagonia.challenge_it_patagonia.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/empresa")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "It-Patagonia API", description = "Challenge it-patagonia")
public class EmpresaController {

    private final EmpresaService empresaService;

    @Operation(
            summary ="Retorna las empresas que se adhirieron el último mes.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Si no hay empresas en el último mes"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            })
    @GetMapping("/get-emp-adheridas-ult-mes")
    public ResponseEntity<List<EmpresaResponse>> getEmpresasAdheridasUltimoMes() {
        try {
            var empresas = empresaService.obtenerEmpresasAdheridasUltimoMes();

            if (empresas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(empresas);

        } catch (DataAccessException e) {
            log.error("Error en la base de datos al buscar las empresas que se adhirieron el último mes.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception e) {
            log.error("Error inesperado al buscar las empresas que se adhirieron el último mes.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary ="Adherir una empresa",
            description = "Guarda una nueva empresa en la base de datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la empresa a registrar",
                    required = true
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Empresa creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error de validación",
                            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            })
    @PostMapping("/adhesion")
    public ResponseEntity<?> adhesion(@Valid @RequestBody EmpresaRequest request) {
        log.info("Request: {}", request);

        try {
            var empresaCreada = empresaService.adherirEmpresa(request);

            if (empresaCreada.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDefault("La empresa ya está registrada."));
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(empresaCreada);

        } catch (DataAccessException e) {
            log.error("Error en la base de datos al adherir la empresa", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDefault("Error de base de datos. Intente más tarde."));
        }
        catch (Exception e) {
            log.error("Error inesperado al adherir la empresa", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDefault("Ocurrió un error inesperado."));
        }
    }
}