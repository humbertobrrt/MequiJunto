package com.dev.kallopolli.mequijunto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import com.dev.kallopolli.mequijunto.converter.EscalaTrabalhoConverter;
import com.dev.kallopolli.mequijunto.enums.EscalaTrabalho;

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

    @Convert(converter = EscalaTrabalhoConverter.class)
    @Column(name = "escala", length = 10)
    private EscalaTrabalho escala;

    @Column(name = "ultima_interacao")
    private OffsetDateTime ultimaInteracao;

}
