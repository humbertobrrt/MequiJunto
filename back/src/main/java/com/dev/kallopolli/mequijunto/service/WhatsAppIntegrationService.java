package com.dev.kallopolli.mequijunto.service;

import com.dev.kallopolli.mequijunto.dto.WhatsAppEventoRequest;
import com.dev.kallopolli.mequijunto.dto.WhatsAppEventoResponse;
import com.dev.kallopolli.mequijunto.entity.Conversa;
import com.dev.kallopolli.mequijunto.entity.Mensagem;
import com.dev.kallopolli.mequijunto.entity.Usuario;
import com.dev.kallopolli.mequijunto.repository.MensagemRepository;
import com.dev.kallopolli.mequijunto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class WhatsAppIntegrationService {

    private final UsuarioRepository usuarioRepository;
    private final MensagemRepository mensagemRepository;
    private final ConversaService conversaService;

    @Transactional
    public WhatsAppEventoResponse registrarEvento(WhatsAppEventoRequest request) {
        String telefone = UsuarioService.normalizarTelefone(request.telefone());
        Usuario usuario = usuarioRepository.findByTelefoneAndAtivoTrue(telefone)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Nenhum colaborador ativo foi encontrado para esse telefone."));

        Conversa conversa = conversaService.obterOuCriarConversaAtiva(usuario.getId());

        String statusAlerta = request.statusAlerta();
        if (statusAlerta != null && !statusAlerta.isBlank()) {
            statusAlerta = statusAlerta.trim().toUpperCase();
            if (!statusAlerta.equals("PENDENTE") && !statusAlerta.equals("EM_ANALISE") && !statusAlerta.equals("RESOLVIDO")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status de alerta inválido.");
            }
        }
        if ((statusAlerta == null || statusAlerta.isBlank()) && deveCriarAlerta(request.nivelRisco())) {
            statusAlerta = "PENDENTE";
        }

        Mensagem mensagemColaborador = new Mensagem();
        mensagemColaborador.setConversa(conversa);
        mensagemColaborador.setOrigem("COLABORADOR");
        mensagemColaborador.setTipo(request.tipo() == null ? "TEXTO" : request.tipo());
        mensagemColaborador.setConteudo(request.conteudo());
        mensagemColaborador.setAudioUrl(request.audioUrl());
        mensagemColaborador.setTranscricao(request.transcricao());
        mensagemColaborador.setNotaDia(request.notaDia());
        mensagemColaborador.setAnaliseIa(request.analiseIa());
        mensagemColaborador.setNivelRisco(request.nivelRisco());
        mensagemColaborador.setTipoSinal(request.tipoSinal());
        mensagemColaborador.setDestinoAlerta(request.destinoAlerta());
        mensagemColaborador.setStatusAlerta(statusAlerta);
        mensagemColaborador = mensagemRepository.save(mensagemColaborador);

        Long mensagemIaId = null;
        if (request.respostaIa() != null && !request.respostaIa().isBlank()) {
            Mensagem mensagemIa = new Mensagem();
            mensagemIa.setConversa(conversa);
            mensagemIa.setOrigem("IA");
            mensagemIa.setTipo("TEXTO");
            mensagemIa.setConteudo(request.respostaIa());
            mensagemIa = mensagemRepository.save(mensagemIa);
            mensagemIaId = mensagemIa.getId();
        }

        return new WhatsAppEventoResponse(
                conversa.getId(), mensagemColaborador.getId(), mensagemIaId,
                mensagemColaborador.getStatusAlerta() != null
        );
    }

    private boolean deveCriarAlerta(String nivelRisco) {
        if (nivelRisco == null || nivelRisco.isBlank()) return false;
        String nivel = nivelRisco.trim().toUpperCase();
        return !nivel.equals("BAIXO") && !nivel.equals("NENHUM") && !nivel.equals("SEM_RISCO");
    }
}
