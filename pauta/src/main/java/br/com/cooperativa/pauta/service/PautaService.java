package br.com.cooperativa.pauta.service;

import br.com.cooperativa.pauta.base.BaseService;
import br.com.cooperativa.pauta.dto.PautaDTO;
import br.com.cooperativa.pauta.dto.VotoDTO;
import br.com.cooperativa.pauta.entity.Associado;
import br.com.cooperativa.pauta.entity.Pauta;
import br.com.cooperativa.pauta.entity.PautaSessao;
import br.com.cooperativa.pauta.entity.VotoSessao;
import br.com.cooperativa.pauta.enums.StatusPauta;
import br.com.cooperativa.pauta.exception.ObjectNotFoundException;
import br.com.cooperativa.pauta.repository.PautaRepository;
import br.com.cooperativa.pauta.repository.PautaSessaoRepository;
import br.com.cooperativa.pauta.repository.VotoSessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PautaService extends BaseService<Pauta, PautaRepository> {

    @Autowired
    private PautaSessaoRepository pautaSessaoRepository;

    @Autowired
    private VotoSessaoRepository votoSessaoRepository;

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void verificarTempoSessao() {
        List<PautaSessao> sessoesAbertas = pautaSessaoRepository.findByStatus(StatusPauta.VOTACAO_ABERTA);

        for (PautaSessao pautaSessao : sessoesAbertas) {
            if (pautaSessao.getDataFim().isBefore(LocalDateTime.now())) {
                pautaSessao.setStatus(StatusPauta.VOTACAO_FECHADA);
                pautaSessaoRepository.save(pautaSessao);
            }
        }
    }

    @Transactional
    public void abrirSessaoVotacao(Long pautaId, int duracao) {
        Optional<Pauta> pautaOptional = getRepository().findById(pautaId);

        if (pautaOptional.isEmpty()) {
            throw new ObjectNotFoundException("Pauta não encontrada com o id: " + pautaId);
        }

        Pauta pauta = pautaOptional.get();

        PautaSessao pautaSessao = new PautaSessao();
        pautaSessao.setPauta(pauta);
        pautaSessao.setDataInicio(LocalDateTime.now());
        pautaSessao.setDataFim(pautaSessao.getDataInicio().plusMinutes(duracao));
        pautaSessao.setStatus(StatusPauta.VOTACAO_ABERTA);
        pautaSessaoRepository.save(pautaSessao);
    }

    @Transactional
    public void registrarVoto(Long pautaSessaoId, VotoDTO votoDTO) {
        Optional<PautaSessao> pautaSessaoOptional = pautaSessaoRepository.findById(pautaSessaoId);

        if (pautaSessaoOptional.isEmpty()) {
            throw new ObjectNotFoundException("Sessão de votação não encontrada com o id: " + pautaSessaoId);
        }

        PautaSessao pautaSessao = pautaSessaoOptional.get();

        if (pautaSessao.getStatus() != StatusPauta.VOTACAO_ABERTA) {
            throw new IllegalStateException("A sessão de votação está fechada ou não foi aberta.");
        }

        Optional<VotoSessao> votoExistente = votoSessaoRepository.findByPautaSessaoIdAndAssociadoId(pautaSessaoId, votoDTO.getAssociadoId());
        if (votoExistente.isPresent()) {
            throw new IllegalStateException("O associado já votou nesta sessão.");
        }

        VotoSessao votoSessao = new VotoSessao();
        votoSessao.setPautaSessao(pautaSessao);
        votoSessao.setAssociado(new Associado(votoDTO.getAssociadoId()));
        votoSessao.setVoto(votoDTO.getVoto());
        votoSessaoRepository.save(votoSessao);
    }

    @Transactional
    public String contabilizarResultado(Long pautaSessaoId) {
        Optional<PautaSessao> pautaSessaoOptional = pautaSessaoRepository.findById(pautaSessaoId);

        if (pautaSessaoOptional.isEmpty()) {
            throw new ObjectNotFoundException("Sessão de votação não encontrada com o id: " + pautaSessaoId);
        }

        PautaSessao pautaSessao = pautaSessaoOptional.get();

        if (pautaSessao.getStatus() != StatusPauta.VOTACAO_FECHADA) {
            throw new IllegalStateException("A sessão de votação ainda está aberta.");
        }

        List<VotoSessao> lVotosSessao = votoSessaoRepository.findAllByPautaSessaoId(pautaSessaoId);

        //TODO
        return String.format("Resultado");
    }

    public void cadastrarPauta(PautaDTO pautaDTO) {
        //TODO
    }
}