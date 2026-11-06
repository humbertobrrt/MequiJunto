package com.dev.kallopolli.mequijunto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "alertas")
@Getter
@Setter
@NoArgsConstructor
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "tipo_sinal", length = 100)
    private String tipoSinal;

    @Column(name = "analise_ia", nullable = false, columnDefinition = "text")
    private String analiseIa;

    @Column(name = "destino_alerta", length = 100)
    private String destinoAlerta;

    @Column(name = "status_alerta", nullable = false, length = 20)
    private String statusAlerta = "PENDENTE";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    @Column(name = "retorno_em")
    private OffsetDateTime retornoEm;

    @Column(name = "observacao_retorno", columnDefinition = "text")
    private String observacaoRetorno;
}
