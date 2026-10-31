package com.dev.kallopolli.mequijunto.repository;

import com.dev.kallopolli.mequijunto.entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByConversa_IdOrderByCriadoEmAsc(Long conversaId);
    List<Mensagem> findByStatusAlertaIsNotNullOrderByCriadoEmDesc();
    List<Mensagem> findByStatusAlertaIgnoreCaseOrderByCriadoEmDesc(String statusAlerta);
    long countByStatusAlerta(String statusAlerta);
    long countByStatusAlertaIsNotNullAndNivelRiscoIgnoreCase(String nivelRisco);
}
