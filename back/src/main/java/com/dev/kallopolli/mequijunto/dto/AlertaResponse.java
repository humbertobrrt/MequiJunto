package com.dev.kallopolli.mequijunto.dto;

import java.time.OffsetDateTime;

public record AlertaResponse(
        Long id,
        Long usuarioId,
        String nomeUsuario,
        String telefoneUsuario,
        String analiseIa,
        String tipoSinal,
        String destinoAlerta,
        String statusAlerta,
        OffsetDateTime criadoEm,
        Long responsavelId,
        String nomeResponsavel,
        OffsetDateTime retornoEm,
        String observacaoRetorno
) {}
