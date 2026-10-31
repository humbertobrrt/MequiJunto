package com.dev.kallopolli.mequijunto.service;

import com.dev.kallopolli.mequijunto.dto.AtualizarColaboradorRequest;
import com.dev.kallopolli.mequijunto.dto.CadastroColaboradorRequest;
import com.dev.kallopolli.mequijunto.dto.UsuarioResponse;
import com.dev.kallopolli.mequijunto.entity.Usuario;
import com.dev.kallopolli.mequijunto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final String PERFIL_COLABORADOR = "COLABORADOR";
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findByPerfilAcessoOrderByNomeAsc(PERFIL_COLABORADOR)
                .stream().map(this::paraResponse).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscar(Long id) {
        return paraResponse(buscarColaborador(id));
    }

    @Transactional
    public UsuarioResponse cadastrar(CadastroColaboradorRequest request) {
        String telefone = normalizarTelefone(request.telefone());
        if (telefone.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe um telefone válido.");
        }
        if (usuarioRepository.findByTelefone(telefone).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um colaborador com esse telefone.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.nome().trim());
        usuario.setTelefone(telefone);
        usuario.setCargo(request.cargo().trim());
        usuario.setIdade(request.idade());
        usuario.setGenero(request.genero());
        usuario.setDataIngresso(request.dataIngresso());
        usuario.setAtivo(true);
        usuario.setPerfilAcesso(PERFIL_COLABORADOR);
        // Colaboradores não possuem login na plataforma web.
        usuario.setEmail(null);
        usuario.setSenhaHash(null);

        return paraResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponse atualizar(Long id, AtualizarColaboradorRequest request) {
        Usuario usuario = buscarColaborador(id);
        String telefone = normalizarTelefone(request.telefone());
        if (telefone.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe um telefone válido.");
        }
        usuarioRepository.findByTelefone(telefone).ifPresent(outro -> {
            if (!outro.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Esse telefone já está cadastrado para outro colaborador.");
            }
        });

        usuario.setNome(request.nome().trim());
        usuario.setTelefone(telefone);
        usuario.setCargo(request.cargo().trim());
        usuario.setIdade(request.idade());
        usuario.setGenero(request.genero());
        usuario.setDataIngresso(request.dataIngresso());
        usuario.setAtivo(request.ativo());
        return paraResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void desativar(Long id) {
        Usuario usuario = buscarColaborador(id);
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarEntidade(Long id) {
        return buscarColaborador(id);
    }

    private Usuario buscarColaborador(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Colaborador não encontrado."));
        if (!PERFIL_COLABORADOR.equals(usuario.getPerfilAcesso())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Colaborador não encontrado.");
        }
        return usuario;
    }

    private UsuarioResponse paraResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(), usuario.getNome(), usuario.getTelefone(), usuario.getCargo(),
                usuario.getAtivo(), usuario.getIdade(), usuario.getGenero(),
                usuario.getDataIngresso(), usuario.getPerfilAcesso()
        );
    }

    public static String normalizarTelefone(String telefone) {
        if (telefone == null) return "";
        return telefone.replaceAll("\\D", "");
    }
}
