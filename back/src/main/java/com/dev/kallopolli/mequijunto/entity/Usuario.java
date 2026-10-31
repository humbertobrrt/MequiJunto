package com.dev.kallopolli.mequijunto.entity;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String nome;

    private String telefone;
    private String cargo;
    private Boolean ativo = true;
    private Integer idade;
    private String genero;

    @Column(name = "data_ingresso")
    private LocalDate dataIngresso;

    private String email;

    @Column(name = "senha_hash", columnDefinition = "text")
    private String senhaHash;

    @Column(name = "perfil_acesso", length = 20)
    private String perfilAcesso = "COLABORADOR";
}
