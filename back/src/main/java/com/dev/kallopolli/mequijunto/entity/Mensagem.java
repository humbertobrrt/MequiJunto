package com.dev.kallopolli.mequijunto.entity;

import jakarta.persistence.*;
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

    // O banco atual do projeto utiliza a coluna conversas_id.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversas_id", nullable = false)
    private Conversa conversa;

    private String origem;
    private String tipo;

    @Column(columnDefinition = "text")
    private String conteudo;

    @Column(name = "audio_url", columnDefinition = "text")
    private String audioUrl;

    @Column(columnDefinition = "text")
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

    @Column(name = "tipo_sinal")
    private String tipoSinal;

    @Column(name = "destino_alerta")
    private String destinoAlerta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    @Column(name = "retorno_em")
    private LocalDateTime retornoEm;

    @Column(name = "observacao_retorno", columnDefinition = "text")
    private String observacaoRetorno;

    @PrePersist
    public void prePersist() {
        if (criadoEm == null) criadoEm = LocalDateTime.now();
    }
}
