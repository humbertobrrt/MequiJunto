package com.dev.kallopolli.mequijunto.service;

import com.dev.kallopolli.mequijunto.dto.AlertaResponse;
import com.dev.kallopolli.mequijunto.dto.AtualizarAlertaRequest;
import com.dev.kallopolli.mequijunto.entity.Mensagem;
import com.dev.kallopolli.mequijunto.entity.Usuario;
import com.dev.kallopolli.mequijunto.repository.MensagemRepository;
import com.dev.kallopolli.mequijunto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private static final Set<String> STATUS_VALIDOS = Set.of("PENDENTE", "EM_ANALISE", "RESOLVIDO");
    private final MensagemRepository mensagemRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<AlertaResponse> listar(String status) {
        List<Mensagem> mensagens = (status == null || status.isBlank())
                ? mensagemRepository.findByStatusAlertaIsNotNullOrderByCriadoEmDesc()
                : mensagemRepository.findByStatusAlertaIgnoreCaseOrderByCriadoEmDesc(status.trim());
        return mensagens.stream().map(this::paraResponse).toList();
    }

    @Transactional
    public AlertaResponse atualizar(Long mensagemId, AtualizarAlertaRequest request) {
        String novoStatus = request.statusAlerta().trim().toUpperCase();
        if (!STATUS_VALIDOS.contains(novoStatus)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status inválido. Use PENDENTE, EM_ANALISE ou RESOLVIDO.");
        }

        Mensagem mensagem = mensagemRepository.findById(mensagemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerta não encontrado."));
        if (mensagem.getStatusAlerta() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerta não encontrado.");
        }

        Usuario responsavel = usuarioRepository.findById(request.responsavelId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Responsável não encontrado."));
        if (!Boolean.TRUE.equals(responsavel.getAtivo()) ||
                !("RH".equals(responsavel.getPerfilAcesso()) || "GERENCIA".equals(responsavel.getPerfilAcesso()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "O responsável precisa ser um usuário ativo de RH ou Gerência.");
        }

        mensagem.setStatusAlerta(novoStatus);
        mensagem.setResponsavel(responsavel);
        mensagem.setRetornoEm(LocalDateTime.now());
        mensagem.setObservacaoRetorno(request.observacaoRetorno());
        return paraResponse(mensagemRepository.save(mensagem));
    }

    private AlertaResponse paraResponse(Mensagem mensagem) {
        Usuario colaborador = mensagem.getConversa().getUsuario();
        Usuario responsavel = mensagem.getResponsavel();
        return new AlertaResponse(
                mensagem.getId(), colaborador.getId(), colaborador.getNome(), colaborador.getTelefone(),
                mensagem.getConversa().getId(), mensagem.getConteudo(), mensagem.getAnaliseIa(),
                mensagem.getNivelRisco(), mensagem.getTipoSinal(), mensagem.getDestinoAlerta(),
                mensagem.getStatusAlerta(), mensagem.getCriadoEm(),
                responsavel == null ? null : responsavel.getId(),
                responsavel == null ? null : responsavel.getNome(),
                mensagem.getRetornoEm(), mensagem.getObservacaoRetorno()
        );
    }
}
