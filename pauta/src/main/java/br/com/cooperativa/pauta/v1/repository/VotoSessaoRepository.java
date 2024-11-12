package br.com.cooperativa.pauta.v1.repository;

import br.com.cooperativa.pauta.v1.entity.VotoSessao;
import br.com.cooperativa.pauta.v1.enums.TipoVoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoSessaoRepository extends JpaRepository<VotoSessao, Long> {

    Optional<VotoSessao> findByPautaSessaoIdAndAssociadoId(Long pautaSessaoId, Long associadoId);

    List<VotoSessao> findAllByPautaSessaoId(Long pautaSessaoId);

    Long countByPautaSessaoIdAndVoto(Long pautaSessaoId, TipoVoto voto);
}
