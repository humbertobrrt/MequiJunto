package com.dev.kallopolli.mequijunto.dto;

import java.time.LocalDateTime;

public record ConversaResponse(
        Long id,
        Long usuarioId,
        String nomeUsuario,
        LocalDateTime inicio,
        LocalDateTime fim,
        String status,
        String resumo
) {}
