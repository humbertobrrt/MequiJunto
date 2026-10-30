package com.dev.kallopolli.mequijunto.repository;

import com.dev.kallopolli.mequijunto.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
