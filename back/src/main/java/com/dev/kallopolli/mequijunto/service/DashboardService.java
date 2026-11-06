package com.dev.kallopolli.mequijunto.service;

import com.dev.kallopolli.mequijunto.dto.DashboardResumoResponse;
import com.dev.kallopolli.mequijunto.repository.AlertaRepository;
import com.dev.kallopolli.mequijunto.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UsuarioRepository usuarioRepository;
    private final AlertaRepository alertaRepository;

    @Transactional(readOnly = true)
    public DashboardResumoResponse obterResumo() {

        long total = usuarioRepository.countByPerfilAcesso("COLABORADOR");

        long ativos = usuarioRepository.countByPerfilAcessoAndAtivoTrue(
                "COLABORADOR"
        );

        LocalDate limite = LocalDate.now().minusDays(90);

        long primeiros90Dias = usuarioRepository
                .findByPerfilAcessoAndAtivoTrue("COLABORADOR")
                .stream()
                .filter(usuario -> usuario.getDataIngresso() != null)
                .filter(usuario -> !usuario.getDataIngresso().isBefore(limite))
                .filter(usuario -> !usuario.getDataIngresso().isAfter(LocalDate.now()))
                .count();

        long totalAlertas = alertaRepository.count();

        long alertasPendentes = alertaRepository.countByStatusAlerta(
                "PENDENTE"
        );

        return new DashboardResumoResponse(
                total,
                ativos,
                primeiros90Dias,
                totalAlertas,
                alertasPendentes
        );
    }
}
