package com.dev.kallopolli.mequijunto.service;

import com.dev.kallopolli.mequijunto.dto.AlertaResponse;
import com.dev.kallopolli.mequijunto.dto.AtualizarAlertaRequest;
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
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private static final Set<String> STATUS_VALIDOS = Set.of(
            "PENDENTE",
            "EM_ANALISE",
            "RESOLVIDO"
    );

    private final AlertaRepository alertaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<AlertaResponse> listar(String status) {

        List<Alerta> alertas;

        if (status == null || status.isBlank()) {
            alertas = alertaRepository.findAllByOrderByCreatedAtDesc();
        } else {
            alertas = alertaRepository
                    .findByStatusAlertaIgnoreCaseOrderByCreatedAtDesc(
                            status.trim()
                    );
        }

        return alertas.stream()
                .map(this::paraResponse)
                .toList();
    }

    @Transactional
    public AlertaResponse atualizar(
            Long alertaId,
            AtualizarAlertaRequest request
    ) {

        String novoStatus = request.statusAlerta()
                .trim()
                .toUpperCase();

        if (!STATUS_VALIDOS.contains(novoStatus)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Status inválido."
            );
        }

        Alerta alerta = alertaRepository.findById(alertaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Alerta não encontrado."
                ));

        Usuario responsavel = usuarioRepository
                .findById(request.responsavelId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Responsável não encontrado."
                ));

        if (!Boolean.TRUE.equals(responsavel.getAtivo())
                || !("RH".equals(responsavel.getPerfilAcesso())
                || "GERENCIA".equals(responsavel.getPerfilAcesso()))) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "O responsável precisa ser um usuário ativo de RH ou Gerência."
            );
        }

        alerta.setStatusAlerta(novoStatus);
        alerta.setResponsavel(responsavel);
        alerta.setRetornoEm(OffsetDateTime.now());
        alerta.setObservacaoRetorno(request.observacaoRetorno());

        return paraResponse(alertaRepository.save(alerta));
    }

    private AlertaResponse paraResponse(Alerta alerta) {

        Usuario colaborador = alerta.getUsuario();
        Usuario responsavel = alerta.getResponsavel();

        return new AlertaResponse(
                alerta.getId(),
                colaborador.getId(),
                colaborador.getNome(),
                colaborador.getTelefone(),
                alerta.getAnaliseIa(),
                alerta.getTipoSinal(),
                alerta.getDestinoAlerta(),
                alerta.getStatusAlerta(),
                alerta.getCreatedAt(),
                responsavel == null ? null : responsavel.getId(),
                responsavel == null ? null : responsavel.getNome(),
                alerta.getRetornoEm(),
                alerta.getObservacaoRetorno()
        );
    }
}
