package com.dev.kallopolli.mequijunto.dto;

import java.time.LocalDateTime;

public record AlertaResponse(
        Long id,
        Long usuarioId,
        String nomeUsuario,
        String telefoneUsuario,
        Long conversaId,
        String conteudo,
        String analiseIa,
        String nivelRisco,
        String tipoSinal,
        String destinoAlerta,
        String statusAlerta,
        LocalDateTime criadoEm,
        Long responsavelId,
        String nomeResponsavel,
        LocalDateTime retornoEm,
        String observacaoRetorno
) {}
