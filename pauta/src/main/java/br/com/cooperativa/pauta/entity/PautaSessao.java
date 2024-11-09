package br.com.cooperativa.pauta.entity;

import br.com.cooperativa.pauta.enums.StatusPauta;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class PautaSessao {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pauta_id", nullable = false)
    private Pauta pauta;

    @Column(name = "DATA_HORA_INICIO", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "DATA_HORA_FIM", nullable = false)
    private LocalDateTime dataFim;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPauta status = StatusPauta.FECHADO;

    @OneToMany(mappedBy = "sessaoVotacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VotoSessao> votos;

}
