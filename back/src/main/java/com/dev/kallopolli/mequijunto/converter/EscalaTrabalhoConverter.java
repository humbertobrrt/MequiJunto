package com.dev.kallopolli.mequijunto.converter;

import com.dev.kallopolli.mequijunto.enums.EscalaTrabalho;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EscalaTrabalhoConverter implements AttributeConverter<EscalaTrabalho, String> {

    @Override
    public String convertToDatabaseColumn(EscalaTrabalho escala) {
        return escala == null ? null : escala.getValor();
    }

    @Override
    public EscalaTrabalho convertToEntityAttribute(String valor) {
        return valor == null ? null : EscalaTrabalho.deValor(valor);
    }
}
