package com.dev.kallopolli.mequijunto.service;

import com.dev.kallopolli.mequijunto.entity.Conversa;
import com.dev.kallopolli.mequijunto.entity.Mensagem;
import com.dev.kallopolli.mequijunto.repository.ConversaRepository;
import com.dev.kallopolli.mequijunto.repository.MensagemRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final ConversaRepository conversaRepository;

    @Transactional(readOnly = true)
    public List<Mensagem> listarTodas() {
        return mensagemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Mensagem buscarPorId(Long id) {
        return mensagemRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Mensagem não encontrada: " + id
                        ));
    }

    @Transactional(readOnly = true)
    public List<Mensagem> listarPorConversa(Long conversaId) {
        if (!conversaRepository.existsById(conversaId)) {
            throw new EntityNotFoundException(
                    "Conversa não encontrada: " + conversaId
            );
        }

        return mensagemRepository
                .findByConversa_IdOrderByCriadoEmAsc(conversaId);
    }

    @Transactional
    public Mensagem salvar(Mensagem mensagem, Long conversaId) {
        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Conversa não encontrada: " + conversaId
                        ));

        mensagem.setConversa(conversa);

        return mensagemRepository.save(mensagem);
    }

    @Transactional
    public Mensagem atualizar(
            Long id,
            Mensagem dados,
            Long conversaId
    ) {
        Mensagem mensagem = buscarPorId(id);

        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Conversa não encontrada: " + conversaId
                        ));

        mensagem.setConversa(conversa);
        mensagem.setOrigem(dados.getOrigem());
        mensagem.setTipo(dados.getTipo());
        mensagem.setConteudo(dados.getConteudo());
        mensagem.setAudioUrl(dados.getAudioUrl());
        mensagem.setTranscricao(dados.getTranscricao());
        mensagem.setNotaDia(dados.getNotaDia());
        mensagem.setAnaliseIa(dados.getAnaliseIa());
        mensagem.setNivelRisco(dados.getNivelRisco());
        mensagem.setStatusAlerta(dados.getStatusAlerta());

        return mensagemRepository.save(mensagem);
    }

    @Transactional
    public void excluir(Long id) {
        Mensagem mensagem = buscarPorId(id);
        mensagemRepository.delete(mensagem);
    }
}
