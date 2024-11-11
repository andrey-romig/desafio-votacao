package br.com.cooperativa.pauta.v1.repository;

import br.com.cooperativa.pauta.v1.entity.PautaSessao;
import br.com.cooperativa.pauta.v1.enums.StatusPauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PautaSessaoRepository extends JpaRepository<PautaSessao, Long> {

    List<PautaSessao> findByStatus(StatusPauta statusPauta);
}
