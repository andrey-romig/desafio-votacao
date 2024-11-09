package br.com.cooperativa.pauta.repository;

import br.com.cooperativa.pauta.entity.PautaSessao;
import br.com.cooperativa.pauta.enums.StatusPauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PautaSessaoRepository extends JpaRepository<PautaSessao, Long> {

    List<PautaSessao> findByStatus(StatusPauta statusPauta);
}
