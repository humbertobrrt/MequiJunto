package com.dev.kallopolli.mequijunto.service;

import com.dev.kallopolli.mequijunto.dto.ConversaResponse;
import com.dev.kallopolli.mequijunto.entity.Conversa;
import com.dev.kallopolli.mequijunto.entity.Usuario;
import com.dev.kallopolli.mequijunto.repository.ConversaRepository;
import com.dev.kallopolli.mequijunto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversaService {

    private final ConversaRepository conversaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<ConversaResponse> listar(Long usuarioId) {
        List<Conversa> conversas;
        if (usuarioId == null) {
            conversas = conversaRepository.findAllByOrderByInicioDesc();
        } else {
            conversas = conversaRepository.findByUsuario_IdOrderByInicioDesc(usuarioId);
        }
        return conversas.stream().map(this::paraResponse).toList();
    }

    @Transactional
    public Conversa obterOuCriarConversaAtiva(Long usuarioId) {
        return conversaRepository.findFirstByUsuario_IdAndFimIsNullOrderByInicioDesc(usuarioId)
                .orElseGet(() -> {
                    Usuario usuario = usuarioRepository.findById(usuarioId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Colaborador não encontrado."));
                    Conversa conversa = new Conversa();
                    conversa.setUsuario(usuario);
                    conversa.setInicio(LocalDateTime.now());
                    conversa.setStatus("ATIVA");
                    return conversaRepository.save(conversa);
                });
    }

    private ConversaResponse paraResponse(Conversa conversa) {
        return new ConversaResponse(
                conversa.getId(), conversa.getUsuario().getId(), conversa.getUsuario().getNome(),
                conversa.getInicio(), conversa.getFim(), conversa.getStatus(), conversa.getResumo()
        );
    }
}
