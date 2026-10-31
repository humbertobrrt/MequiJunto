package com.dev.kallopolli.mequijunto.dto;

import jakarta.validation.constraints.NotBlank;

public record WhatsAppEventoRequest(
        @NotBlank String telefone,
        String tipo,
        String conteudo,
        String audioUrl,
        String transcricao,
        String respostaIa,
        Short notaDia,
        String analiseIa,
        String nivelRisco,
        String tipoSinal,
        String destinoAlerta,
        String statusAlerta
) {}
