package br.com.cooperativa.pauta.v1.entity;

import br.com.cooperativa.pauta.v1.enums.StatusPauta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PautaSessao {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pauta_id", nullable = false)
    private Pauta pauta;

    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_hora_fim", nullable = false)
    private LocalDateTime dataFim;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPauta status = StatusPauta.FECHADO;

    @OneToMany(mappedBy = "pautaSessao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VotoSessao> votos;

}
