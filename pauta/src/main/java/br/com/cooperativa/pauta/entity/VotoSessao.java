package br.com.cooperativa.pauta.entity;

import br.com.cooperativa.pauta.enums.TipoVoto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VotoSessao {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "VOTO", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoVoto voto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessao_id", nullable = false)
    private PautaSessao pautaSessao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "associado_id", nullable = false)
    private Associado associado;

}
