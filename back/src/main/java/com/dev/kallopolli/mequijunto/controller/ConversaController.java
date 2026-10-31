package com.dev.kallopolli.mequijunto.controller;

import com.dev.kallopolli.mequijunto.dto.ConversaResponse;
import com.dev.kallopolli.mequijunto.service.ConversaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/conversas")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('RH', 'GERENCIA')")
@SecurityRequirement(name = "bearerAuth")
public class ConversaController {

    private final ConversaService conversaService;

    @GetMapping
    public ResponseEntity<List<ConversaResponse>> listar(
            @RequestParam(required = false) Long usuarioId
    ) {
        return ResponseEntity.ok(conversaService.listar(usuarioId));
    }
}
