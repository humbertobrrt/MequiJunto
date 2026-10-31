package com.dev.kallopolli.mequijunto.service;

import com.dev.kallopolli.mequijunto.dto.MensagemResponse;
import com.dev.kallopolli.mequijunto.entity.Mensagem;
import com.dev.kallopolli.mequijunto.repository.MensagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MensagemService {

    private final MensagemRepository mensagemRepository;

    @Transactional(readOnly = true)
    public List<MensagemResponse> listarPorConversa(Long conversaId) {
        return mensagemRepository.findByConversa_IdOrderByCriadoEmAsc(conversaId)
                .stream().map(this::paraResponse).toList();
    }

    @Transactional(readOnly = true)
    public Mensagem buscarEntidade(Long id) {
        return mensagemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mensagem não encontrada."));
    }

    @Transactional
    public Mensagem salvar(Mensagem mensagem) {
        return mensagemRepository.save(mensagem);
    }

    private MensagemResponse paraResponse(Mensagem mensagem) {
        return new MensagemResponse(
                mensagem.getId(), mensagem.getConversa().getId(), mensagem.getOrigem(), mensagem.getTipo(),
                mensagem.getConteudo(), mensagem.getAudioUrl(), mensagem.getTranscricao(), mensagem.getCriadoEm(),
                mensagem.getNotaDia(), mensagem.getAnaliseIa(), mensagem.getNivelRisco(), mensagem.getStatusAlerta()
        );
    }
}
