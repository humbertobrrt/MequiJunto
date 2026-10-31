package com.dev.kallopolli.mequijunto.dto;

import java.time.LocalDateTime;

public record MensagemResponse(
        Long id,
        Long conversaId,
        String origem,
        String tipo,
        String conteudo,
        String audioUrl,
        String transcricao,
        LocalDateTime criadoEm,
        Short notaDia,
        String analiseIa,
        String nivelRisco,
        String statusAlerta
) {}
