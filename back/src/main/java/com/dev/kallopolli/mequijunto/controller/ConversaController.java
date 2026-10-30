
package com.dev.kallopolli.mequijunto.controller;

import com.dev.kallopolli.mequijunto.entity.Conversa;
import com.dev.kallopolli.mequijunto.service.ConversaService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversas")
@RequiredArgsConstructor
public class ConversaController {

    private final ConversaService conversaService;

    // Listar todas as conversas
    @GetMapping
    public ResponseEntity<List<Conversa>> listarTodas() {
        return ResponseEntity.ok(conversaService.listarTodas());
    }

    // Buscar conversa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Conversa> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(conversaService.buscarPorId(id));
    }

    // Listar conversas de um usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Conversa>> listarPorUsuario(
            @PathVariable Long usuarioId) {

        return ResponseEntity.ok(
                conversaService.listarPorUsuario(usuarioId)
        );
    }

    // Cadastrar conversa vinculada a um usuário
    @PostMapping
    public ResponseEntity<Conversa> salvar(
            @RequestBody Conversa conversa,
            @RequestParam Long usuarioId) {

        Conversa novaConversa = conversaService.salvar(conversa, usuarioId);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaConversa);
    }

    // Atualizar conversa
    @PutMapping("/{id}")
    public ResponseEntity<Conversa> atualizar(
            @PathVariable Long id,
            @RequestParam Long usuarioId,
            @RequestBody Conversa conversa) {

        return ResponseEntity.ok(
                conversaService.atualizar(id, conversa, usuarioId)
        );
    }

    // Excluir conversa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        conversaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}

