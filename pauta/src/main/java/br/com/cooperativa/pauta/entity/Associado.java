package br.com.cooperativa.pauta.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Associado {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "CPF", nullable = false, unique = true)
    private String cpf;

    @OneToMany(mappedBy = "associado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VotoSessao> votos;

    public Associado(Long id) {
        this.id = id;
    }
}
