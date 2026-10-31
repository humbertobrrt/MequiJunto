package com.dev.kallopolli.mequijunto.config;

import com.dev.kallopolli.mequijunto.entity.Usuario;
import com.dev.kallopolli.mequijunto.repository.UsuarioRepository;
import com.dev.kallopolli.mequijunto.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminBootstrapRunner implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${bootstrap.admin.enabled:false}")
    private boolean enabled;
    @Value("${bootstrap.admin.nome:}")
    private String nome;
    @Value("${bootstrap.admin.email:}")
    private String email;
    @Value("${bootstrap.admin.password:}")
    private String password;
    @Value("${bootstrap.admin.perfil:RH}")
    private String perfil;
    @Value("${bootstrap.admin.telefone:}")
    private String telefone;

    @Override
    public void run(String... args) {
        if (!enabled) return;

        if (nome.isBlank() || email.isBlank() || password.isBlank()) {
            throw new IllegalStateException("Informe nome, e-mail e senha do administrador.");
        }

        perfil = perfil.trim().toUpperCase();
        if (!List.of("RH", "GERENCIA").contains(perfil)) {
            throw new IllegalStateException("O perfil deve ser RH ou GERENCIA.");
        }

        if (usuarioRepository.findByEmailIgnoreCase(email).isPresent()) return;

        Usuario usuario = new Usuario();
        usuario.setNome(nome.trim());
        usuario.setEmail(email.trim().toLowerCase());
        usuario.setSenhaHash(passwordEncoder.encode(password));
        usuario.setPerfilAcesso(perfil);
        usuario.setCargo("ADMINISTRATIVO");
        usuario.setTelefone(telefone == null || telefone.isBlank()
                ? null : UsuarioService.normalizarTelefone(telefone));
        usuario.setAtivo(true);
        usuario.setDataIngresso(LocalDate.now());
        usuarioRepository.save(usuario);
    }
}
