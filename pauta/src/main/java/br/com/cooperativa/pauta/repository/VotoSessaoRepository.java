package br.com.cooperativa.pauta.repository;

import br.com.cooperativa.pauta.entity.VotoSessao;
import br.com.cooperativa.pauta.enums.TipoVoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoSessaoRepository extends JpaRepository<VotoSessao, Long> {

    Optional<VotoSessao> findByPautaIdAndAssociadoId(Long pautaId, Long associadoId);

    Optional<VotoSessao> findByPautaSessaoIdAndAssociadoId(Long pautaSessaoId, Long associadoId);

    List<VotoSessao> findAllByPautaSessaoId(Long pautaSessaoId);
}
