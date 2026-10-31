package com.dev.kallopolli.mequijunto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AtualizarColaboradorRequest(
        @NotBlank String nome,
        @NotBlank String telefone,
        @NotBlank String cargo,
        Integer idade,
        String genero,
        @NotNull LocalDate dataIngresso,
        @NotNull Boolean ativo
) {}
