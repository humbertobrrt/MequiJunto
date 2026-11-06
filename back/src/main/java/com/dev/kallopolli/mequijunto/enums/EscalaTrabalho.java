package com.dev.kallopolli.mequijunto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EscalaTrabalho {
    CINCO_POR_DOIS("5x2"),
    QUATRO_POR_TRES("4x3"),
    DOZE_POR_TRINTA_E_SEIS("12x36");

    private final String valor;

    EscalaTrabalho(String valor) {
        this.valor = valor;
    }

    @JsonValue
    public String getValor() {
        return valor;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static EscalaTrabalho deValor(String valor) {
        for (EscalaTrabalho escala : values()) {
            if (escala.valor.equals(valor)) {
                return escala;
            }
        }
        throw new IllegalArgumentException("Escala inválida. Use 5x2, 4x3 ou 12x36.");
    }
}
