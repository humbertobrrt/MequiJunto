package com.dev.kallopolli.mequijunto.repository;

import com.dev.kallopolli.mequijunto.entity.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findAllByOrderByCreatedAtDesc();

    List<Alerta> findByStatusAlertaIgnoreCaseOrderByCreatedAtDesc(
            String statusAlerta
    );

    long countByStatusAlerta(String statusAlerta);
}
