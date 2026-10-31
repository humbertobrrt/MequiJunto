package com.dev.kallopolli.mequijunto.controller;

import com.dev.kallopolli.mequijunto.dto.MensagemResponse;
import com.dev.kallopolli.mequijunto.service.MensagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('RH', 'GERENCIA')")
public class MensagemController {

    private final MensagemService mensagemService;

    @GetMapping
    public ResponseEntity<List<MensagemResponse>> listar(@RequestParam Long conversaId) {
        return ResponseEntity.ok(mensagemService.listarPorConversa(conversaId));
    }
}
