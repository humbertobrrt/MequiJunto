
package com.dev.kallopolli.mequijunto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(name = "idade")
    private Integer idade;

    @Column(name = "genero")
    private String genero;

    @Column(name = "data_ingresso")
    private LocalDate dataIngresso;

    @Column(name = "email")
    private String email;

    @Column(name = "senha_hash", columnDefinition = "text")
    private String senhaHash;

    @PrePersist
    public void prePersist() {
        if (ativo == null) {
            ativo = true;
        }
    }
}

