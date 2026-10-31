package com.dev.kallopolli.mequijunto.service;

import com.dev.kallopolli.mequijunto.dto.DashboardResumoResponse;
import com.dev.kallopolli.mequijunto.repository.ConversaRepository;
import com.dev.kallopolli.mequijunto.repository.MensagemRepository;
import com.dev.kallopolli.mequijunto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UsuarioRepository usuarioRepository;
    private final ConversaRepository conversaRepository;
    private final MensagemRepository mensagemRepository;

    @Transactional(readOnly = true)
    public DashboardResumoResponse obterResumo() {
        long total = usuarioRepository.countByPerfilAcesso("COLABORADOR");
        long ativos = usuarioRepository.countByPerfilAcessoAndAtivoTrue("COLABORADOR");
        LocalDate limite = LocalDate.now().minusDays(90);
        // O cálculo exato da janela é feito consultando as datas de ingresso dos colaboradores ativos.
        long primeiros90Dias = usuarioRepository.findByPerfilAcessoAndAtivoTrue("COLABORADOR")
                .stream()
                .filter(usuario -> usuario.getDataIngresso() != null)
                .filter(usuario -> !usuario.getDataIngresso().isBefore(limite))
                .filter(usuario -> !usuario.getDataIngresso().isAfter(LocalDate.now()))
                .count();

        return new DashboardResumoResponse(
                total,
                ativos,
                primeiros90Dias,
                conversaRepository.count(),
                mensagemRepository.countByStatusAlerta("PENDENTE"),
                mensagemRepository.countByStatusAlertaIsNotNullAndNivelRiscoIgnoreCase("ALTO")
        );
    }
}
