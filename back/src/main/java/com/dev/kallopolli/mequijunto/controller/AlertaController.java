package com.dev.kallopolli.mequijunto.controller;

import com.dev.kallopolli.mequijunto.dto.AlertaResponse;
import com.dev.kallopolli.mequijunto.dto.AtualizarAlertaRequest;
import com.dev.kallopolli.mequijunto.service.AlertaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('RH', 'GERENCIA')")
@SecurityRequirement(name = "bearerAuth")
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping
    public ResponseEntity<List<AlertaResponse>> listar(@RequestParam(required = false) String status) {
        return ResponseEntity.ok(alertaService.listar(status));
    }

    @PatchMapping("/{id}/retorno")
    public ResponseEntity<AlertaResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AtualizarAlertaRequest request
    ) {
        return ResponseEntity.ok(alertaService.atualizar(id, request));
    }
}
