package com.dev.kallopolli.mequijunto.repository;

import com.dev.kallopolli.mequijunto.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailIgnoreCase(String email);
    Optional<Usuario> findByTelefone(String telefone);
    Optional<Usuario> findByTelefoneAndAtivoTrue(String telefone);
    List<Usuario> findByPerfilAcessoOrderByNomeAsc(String perfilAcesso);
    List<Usuario> findByPerfilAcessoAndAtivoTrue(String perfilAcesso);
    long countByPerfilAcesso(String perfilAcesso);
    long countByPerfilAcessoAndAtivoTrue(String perfilAcesso);
}
