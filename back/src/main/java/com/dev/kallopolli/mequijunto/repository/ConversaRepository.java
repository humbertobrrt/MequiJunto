package com.dev.kallopolli.mequijunto.repository;

import com.dev.kallopolli.mequijunto.entity.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ConversaRepository extends JpaRepository<Conversa, Long> {
    List<Conversa> findByUsuario_Id(Long usuarioId);
    List<Conversa> findByUsuario_IdOrderByInicioDesc(Long usuarioId);
    List<Conversa> findAllByOrderByInicioDesc();
    Optional<Conversa> findFirstByUsuario_IdAndFimIsNullOrderByInicioDesc(Long usuarioId);
}
