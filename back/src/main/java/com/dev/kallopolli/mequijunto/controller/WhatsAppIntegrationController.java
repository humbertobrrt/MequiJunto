package com.dev.kallopolli.mequijunto.controller;

import com.dev.kallopolli.mequijunto.dto.WhatsAppEventoRequest;
import com.dev.kallopolli.mequijunto.dto.WhatsAppEventoResponse;
import com.dev.kallopolli.mequijunto.service.WhatsAppIntegrationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@RestController
@RequestMapping("/api/integracao/whatsapp")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class WhatsAppIntegrationController {

    private final WhatsAppIntegrationService integrationService;

    @Value("${integracao.whatsapp.api-key:}")
    private String apiKeyConfigurada;

    @PostMapping("/evento")
    public ResponseEntity<WhatsAppEventoResponse> registrarEvento(
            @RequestHeader(value = "X-API-KEY", required = false) String apiKeyRecebida,
            @RequestBody @Valid WhatsAppEventoRequest request
    ) {
        if (apiKeyConfigurada == null || apiKeyConfigurada.isBlank() ||
                apiKeyRecebida == null || apiKeyRecebida.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean chaveValida = MessageDigest.isEqual(
                apiKeyConfigurada.getBytes(StandardCharsets.UTF_8),
                apiKeyRecebida.getBytes(StandardCharsets.UTF_8)
        );
        if (!chaveValida) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(integrationService.registrarEvento(request));
    }
}
