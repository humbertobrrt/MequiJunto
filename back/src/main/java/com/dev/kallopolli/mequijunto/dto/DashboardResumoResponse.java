package com.dev.kallopolli.mequijunto.dto;

public record DashboardResumoResponse(

        long totalColaboradores,

        long colaboradoresAtivos,

        long colaboradoresPrimeiros90Dias,

        long totalAlertas,

        long alertasPendentes

) {}
