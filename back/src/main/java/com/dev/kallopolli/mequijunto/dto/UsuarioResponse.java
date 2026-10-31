package com.dev.kallopolli.mequijunto.dto;

import java.time.LocalDate;

public record UsuarioResponse(
        Long id,
        String nome,
        String telefone,
        String cargo,
        Boolean ativo,
        Integer idade,
        String genero,
        LocalDate dataIngresso,
        String perfilAcesso
) {}
