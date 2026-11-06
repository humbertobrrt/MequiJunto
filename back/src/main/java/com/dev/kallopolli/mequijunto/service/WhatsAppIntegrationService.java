package com.dev.kallopolli.mequijunto.service;

import com.dev.kallopolli.mequijunto.dto.WhatsAppEventoRequest;
import com.dev.kallopolli.mequijunto.dto.WhatsAppEventoResponse;
import com.dev.kallopolli.mequijunto.entity.Alerta;
import com.dev.kallopolli.mequijunto.entity.Usuario;
import com.dev.kallopolli.mequijunto.repository.AlertaRepository;
import com.dev.kallopolli.mequijunto.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class WhatsAppIntegrationService {

    private final UsuarioRepository usuarioRepository;
    private final AlertaRepository alertaRepository;

    @Transactional
    public WhatsAppEventoResponse registrarEvento(
            WhatsAppEventoRequest request
    ) {

        String telefone = UsuarioService.normalizarTelefone(
                request.telefone()
        );

        if (telefone.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Telefone inválido."
            );
        }

        Usuario usuario = usuarioRepository
                .findByTelefoneAndAtivoTrue(telefone)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Colaborador não encontrado."
                ));

        // Atualiza a última interação sem salvar a mensagem.
        usuario.setUltimaInteracao(OffsetDateTime.now());

        usuarioRepository.save(usuario);

        // Se não houver alerta, encerra o processamento.
        if (!Boolean.TRUE.equals(request.alertaIdentificado())) {

            return new WhatsAppEventoResponse(
                    false,
                    null,
                    usuario.getId()
            );
        }

        // Se houver alerta, a análise da IA é obrigatória.
        if (request.analiseIa() == null
                || request.analiseIa().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A análise da IA é obrigatória para criar um alerta."
            );
        }

        Alerta alerta = new Alerta();

        alerta.setUsuario(usuario);
        alerta.setTipoSinal(request.tipoSinal());
        alerta.setAnaliseIa(request.analiseIa().trim());
        alerta.setDestinoAlerta(request.destinoAlerta());
        alerta.setStatusAlerta("PENDENTE");

        alerta = alertaRepository.save(alerta);

        return new WhatsAppEventoResponse(
                true,
                alerta.getId(),
                usuario.getId()
        );
    }
}