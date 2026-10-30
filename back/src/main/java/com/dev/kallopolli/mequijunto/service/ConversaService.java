
package com.dev.kallopolli.mequijunto.service;

import com.dev.kallopolli.mequijunto.entity.Conversa;
import com.dev.kallopolli.mequijunto.entity.Usuario;
import com.dev.kallopolli.mequijunto.repository.ConversaRepository;
import com.dev.kallopolli.mequijunto.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversaService {

    private final ConversaRepository conversaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Conversa> listarTodas() {
        return conversaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Conversa buscarPorId(Long id) {
        return conversaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Conversa não encontrada: " + id
                        ));
    }

    @Transactional(readOnly = true)
    public List<Conversa> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new EntityNotFoundException(
                    "Usuário não encontrado: " + usuarioId
            );
        }

        return conversaRepository.findByUsuario_Id(usuarioId);
    }

    @Transactional
    public Conversa salvar(Conversa conversa, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Usuário não encontrado: " + usuarioId
                        ));

        conversa.setUsuario(usuario);

        return conversaRepository.save(conversa);
    }

    @Transactional
    public Conversa atualizar(
            Long id,
            Conversa dados,
            Long usuarioId
    ) {
        Conversa conversa = buscarPorId(id);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Usuário não encontrado: " + usuarioId
                        ));

        conversa.setUsuario(usuario);
        conversa.setInicio(dados.getInicio());
        conversa.setFim(dados.getFim());
        conversa.setStatus(dados.getStatus());
        conversa.setResumo(dados.getResumo());

        return conversaRepository.save(conversa);
    }

    @Transactional
    public void excluir(Long id) {
        Conversa conversa = buscarPorId(id);
        conversaRepository.delete(conversa);
    }
}
