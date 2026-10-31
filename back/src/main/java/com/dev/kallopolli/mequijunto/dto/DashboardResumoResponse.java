package com.dev.kallopolli.mequijunto.dto;

public record DashboardResumoResponse(
        long totalColaboradores,
        long colaboradoresAtivos,
        long colaboradoresPrimeiros90Dias,
        long totalConversas,
        long alertasPendentes,
        long alertasNivelAlto
) {}
