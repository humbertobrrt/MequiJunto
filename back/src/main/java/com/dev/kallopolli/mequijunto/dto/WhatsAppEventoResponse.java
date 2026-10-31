package com.dev.kallopolli.mequijunto.dto;

public record WhatsAppEventoResponse(
        Long conversaId,
        Long mensagemColaboradorId,
        Long mensagemIaId,
        boolean alertaCriado
) {}
