package com.dev.kallopolli.mequijunto.security;

import com.dev.kallopolli.mequijunto.entity.Usuario;
import com.dev.kallopolli.mequijunto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("E-mail ou senha inválidos."));

        if (!Boolean.TRUE.equals(usuario.getAtivo())) {
            throw new UsernameNotFoundException("Usuário inativo.");
        }

        String perfil = usuario.getPerfilAcesso();
        if (!"RH".equals(perfil) && !"GERENCIA".equals(perfil)) {
            throw new UsernameNotFoundException("Usuário sem permissão para acessar a plataforma.");
        }

        if (usuario.getSenhaHash() == null || usuario.getSenhaHash().isBlank()) {
            throw new UsernameNotFoundException("Usuário sem senha cadastrada.");
        }

        return User.withUsername(usuario.getEmail())
                .password(usuario.getSenhaHash())
                .roles(perfil)
                .build();
    }
}
