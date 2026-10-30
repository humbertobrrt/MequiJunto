
package com.dev.kallopolli.mequijunto.controller;

import com.dev.kallopolli.mequijunto.entity.Mensagem;
import com.dev.kallopolli.mequijunto.service.MensagemService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
@RequiredArgsConstructor
public class MensagemController {

    private final MensagemService mensagemService;

    // Listar todas as mensagens
    @GetMapping
    public ResponseEntity<List<Mensagem>> listarTodas() {
        return ResponseEntity.ok(mensagemService.listarTodas());
    }

    // Buscar mensagem por ID
    @GetMapping("/{id}")
    public ResponseEntity<Mensagem> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mensagemService.buscarPorId(id));
    }

    // Listar mensagens de uma conversa
    @GetMapping("/conversa/{conversaId}")
    public ResponseEntity<List<Mensagem>> listarPorConversa(
            @PathVariable Long conversaId) {

        return ResponseEntity.ok(
                mensagemService.listarPorConversa(conversaId)
        );
    }

    // Cadastrar mensagem vinculada a uma conversa
    @PostMapping
    public ResponseEntity<Mensagem> salvar(
            @RequestBody Mensagem mensagem,
            @RequestParam Long conversaId) {

        Mensagem novaMensagem =
                mensagemService.salvar(mensagem, conversaId);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaMensagem);
    }

    // Atualizar mensagem
    @PutMapping("/{id}")
    public ResponseEntity<Mensagem> atualizar(
            @PathVariable Long id,
            @RequestParam Long conversaId,
            @RequestBody Mensagem mensagem) {

        return ResponseEntity.ok(
                mensagemService.atualizar(id, mensagem, conversaId)
        );
    }

    // Excluir mensagem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        mensagemService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
