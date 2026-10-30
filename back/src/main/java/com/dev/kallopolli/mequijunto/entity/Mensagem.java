
package com.dev.kallopolli.mequijunto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensagens")
@Getter
@Setter
@NoArgsConstructor
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversas_id", nullable = false)
    private Conversa conversa;

    @Column(name = "origem")
    private String origem;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "conteudo", columnDefinition = "text")
    private String conteudo;

    @Column(name = "audio_url", columnDefinition = "text")
    private String audioUrl;

    @Column(name = "transcricao", columnDefinition = "text")
    private String transcricao;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "nota_dia")
    private Short notaDia;

    @Column(name = "analise_ia", columnDefinition = "text")
    private String analiseIa;

    @Column(name = "nivel_risco")
    private String nivelRisco;

    @Column(name = "status_alerta")
    private String statusAlerta;

    @PrePersist
    public void prePersist() {
        if (criadoEm == null) {
            criadoEm = LocalDateTime.now();
        }
    }
}
