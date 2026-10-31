package com.dev.kallopolli.mequijunto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizarAlertaRequest(
        @NotBlank String statusAlerta,
        @NotNull Long responsavelId,
        String observacaoRetorno
) {}
