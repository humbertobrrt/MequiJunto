package com.dev.kallopolli.mequijunto.enums;

import com.dev.kallopolli.mequijunto.converter.EscalaTrabalhoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EscalaTrabalhoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EscalaTrabalhoConverter converter = new EscalaTrabalhoConverter();

    @Test
    void serializaELeValoresPublicosEArmazenaOsMesmoValoresNoBanco() throws Exception {
        for (EscalaTrabalho escala : EscalaTrabalho.values()) {
            String json = objectMapper.writeValueAsString(escala);
            assertEquals("\"" + escala.getValor() + "\"", json);
            assertEquals(escala, objectMapper.readValue(json, EscalaTrabalho.class));
            assertEquals(escala.getValor(), converter.convertToDatabaseColumn(escala));
            assertEquals(escala, converter.convertToEntityAttribute(escala.getValor()));
        }
    }

    @Test
    void rejeitaValorForaDasOpcoesPermitidas() {
        assertThrows(Exception.class,
                () -> objectMapper.readValue("\"08h às 16h\"", EscalaTrabalho.class));
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToEntityAttribute("8x5"));
    }
}
