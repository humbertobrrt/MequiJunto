package com.dev.kallopolli.mequijunto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import com.dev.kallopolli.mequijunto.enums.EscalaTrabalho;

public record CadastroColaboradorRequest(
        @NotBlank String nome,
        @NotBlank String telefone,
        @NotBlank String cargo,
        @NotNull EscalaTrabalho escala,
        Integer idade,
        String genero,
        @NotNull LocalDate dataIngresso
) {}
