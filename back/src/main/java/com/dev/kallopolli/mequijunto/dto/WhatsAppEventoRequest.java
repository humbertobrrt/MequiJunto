package com.dev.kallopolli.mequijunto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WhatsAppEventoRequest(

        @NotBlank
        String telefone,

        @NotNull
        Boolean alertaIdentificado,

        String tipoSinal,

        String analiseIa,

        String destinoAlerta

) {}