package com.dev.kallopolli.mequijunto.dto;

import java.time.LocalDate;
import com.dev.kallopolli.mequijunto.enums.EscalaTrabalho;

public record UsuarioResponse(
        Long id,
        String nome,
        String telefone,
        String cargo,
        EscalaTrabalho escala,
        Boolean ativo,
        Integer idade,
        String genero,
        LocalDate dataIngresso,
        String perfilAcesso
) {}
