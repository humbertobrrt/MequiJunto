package com.dev.kallopolli.mequijunto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversas")
@Getter
@Setter
@NoArgsConstructor
public class Conversa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private LocalDateTime inicio;
    private LocalDateTime fim;
    private String status;

    @Column(columnDefinition = "text")
    private String resumo;

    @PrePersist
    public void prePersist() {
        if (inicio == null) inicio = LocalDateTime.now();
        if (status == null) status = "ATIVA";
    }
}
