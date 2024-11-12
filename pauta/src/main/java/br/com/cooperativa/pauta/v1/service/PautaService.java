package br.com.cooperativa.pauta.v1.service;

import br.com.cooperativa.pauta.v1.base.BaseService;
import br.com.cooperativa.pauta.v1.dto.request.PautaRequest;
import br.com.cooperativa.pauta.v1.dto.request.VotoRequest;
import br.com.cooperativa.pauta.v1.dto.response.*;
import br.com.cooperativa.pauta.v1.entity.Associado;
import br.com.cooperativa.pauta.v1.entity.Pauta;
import br.com.cooperativa.pauta.v1.entity.PautaSessao;
import br.com.cooperativa.pauta.v1.entity.VotoSessao;
import br.com.cooperativa.pauta.v1.enums.StatusPauta;
import br.com.cooperativa.pauta.v1.exception.ObjectNotFoundException;
import br.com.cooperativa.pauta.v1.exception.RegraNegocioException;
import br.com.cooperativa.pauta.v1.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PautaService extends BaseService<Pauta, PautaRepository> {

    @Autowired
    private PautaSessaoService pautaSessaoService;

    @Autowired
    private VotoSessaoService votoSessaoService;

    @Autowired
    private AssociadoService associadoService;

    @Scheduled(fixedDelay = 600000) //Verifica as seções a cada 10 minutos
    @Transactional
    public void verificarTempoSessao() {
        List<PautaSessao> sessoesAbertas = pautaSessaoService.getRepository().findByStatus(StatusPauta.VOTACAO_ABERTA);

        for (PautaSessao pautaSessao : sessoesAbertas) {
            if (pautaSessao.getDataFim().isBefore(LocalDateTime.now())) {
                pautaSessao.setStatus(StatusPauta.VOTACAO_FECHADA);
                pautaSessaoService.getRepository().save(pautaSessao);
            }
        }
    }

    @Transactional
    public PautaSessaoResponse abrirSessaoVotacao(Long pautaId, int duracaoSegundos) {
        Optional<Pauta> pautaOptional = getRepository().findById(pautaId);
        if (pautaOptional.isEmpty()) {
            throw new ObjectNotFoundException("Pauta não encontrada com o id: " + pautaId);
        }

        Pauta pauta = pautaOptional.get();

        PautaSessao pautaSessao = new PautaSessao();
        pautaSessao.setPauta(pauta);
        pautaSessao.setDataInicio(LocalDateTime.now());
        pautaSessao.setDataFim(pautaSessao.getDataInicio().plusSeconds(duracaoSegundos));
        pautaSessao.setStatus(StatusPauta.VOTACAO_ABERTA);
        pautaSessao = pautaSessaoService.getRepository().save(pautaSessao);

        return new PautaSessaoResponse(pauta, pautaSessao, new ArrayList<>());
    }

    @Transactional
    public VotoResponse registrarVoto(Long pautaSessaoId, VotoRequest votoDTO) {
        Optional<Associado> associadoOptional = associadoService.getRepository().findById(votoDTO.associadoId());
        if (associadoOptional.isEmpty()) {
            throw new ObjectNotFoundException("Associado não encontrado com o id: " + votoDTO.associadoId());
        }

        Optional<PautaSessao> pautaSessaoOptional = pautaSessaoService.getRepository().findById(pautaSessaoId);
        if (pautaSessaoOptional.isEmpty()) {
            throw new ObjectNotFoundException("Sessão de votação não encontrada com o id: " + pautaSessaoId);
        }

        PautaSessao pautaSessao = pautaSessaoOptional.get();
        Associado associado = associadoOptional.get();

        if (!StatusPauta.VOTACAO_ABERTA.equals(pautaSessao.getStatus())) {
            throw new RegraNegocioException("A sessão de votação está fechada ou não foi aberta.");
        }

        if (pautaSessao.getDataFim().isBefore(LocalDateTime.now())) {
            pautaSessao.setStatus(StatusPauta.VOTACAO_FECHADA);
            pautaSessaoService.getRepository().save(pautaSessao);
            throw new RegraNegocioException("A sessão de votação já foi encerrada.");
        }

        Optional<VotoSessao> votoExistente = votoSessaoService.getRepository().findByPautaSessaoIdAndAssociadoId(pautaSessao.getId(), associado.getId());
        if (votoExistente.isPresent()) {
            throw new RegraNegocioException("O associado já votou nesta sessão.");
        }

        VotoSessao votoSessao = new VotoSessao();
        votoSessao.setPautaSessao(pautaSessao);
        votoSessao.setAssociado(new Associado(associado.getId()));
        votoSessao.setVoto(votoDTO.tipoVoto());
        votoSessao = votoSessaoService.getRepository().save(votoSessao);

        return new VotoResponse(votoSessao, associado);
    }

    @Transactional
    public PautaSessaoResponse contabilizarResultado(Long pautaSessaoId) {
        Optional<PautaSessao> pautaSessaoOptional = pautaSessaoService.getRepository().findById(pautaSessaoId);

        if (pautaSessaoOptional.isEmpty()) {
            throw new ObjectNotFoundException("Sessão de votação não encontrada com o id: " + pautaSessaoId);
        }

        PautaSessao pautaSessao = pautaSessaoOptional.get();

        if (!StatusPauta.VOTACAO_FECHADA.equals(pautaSessao.getStatus())) {
            throw new RegraNegocioException("A sessão de votação ainda está aberta.");
        }

        List<VotoResponse> votoResponses = recuperaVotos(pautaSessaoId);
        return new PautaSessaoResponse(pautaSessao.getPauta(), pautaSessao, votoResponses);
    }

    public PautaResponse cadastrarPauta(PautaRequest pautaDTO) {
        Pauta pauta = new Pauta();
        pauta.setTitulo(pautaDTO.titulo());
        pauta.setDescricao(pautaDTO.descricao());

        pauta = getRepository().save(pauta);

        return new PautaResponse(pauta.getId(), pauta.getTitulo(), pauta.getDescricao());
    }

    public List<PautaAndSessoesResponse> recuperarSessoes() {
        List<Pauta> pautas = getRepository().findAll();

        if (CollectionUtils.isEmpty(pautas)) {
            return Collections.emptyList();
        }

        return pautas.stream()
                .map(pauta -> {
                    PautaResponse pautaResponse = new PautaResponse(pauta);
                    List<SessaoResponse> sessoesResponse =
                            pauta.getSessoes() != null
                                    ? pauta.getSessoes().stream()
                                        .map(sessao -> new SessaoResponse(sessao, getVotos(sessao)))
                                        .toList()
                                    : Collections.emptyList();

                    return new PautaAndSessoesResponse(pautaResponse, sessoesResponse);
                })
                .toList();
    }

    private List<VotoResponse> getVotos(PautaSessao sessao) {
        if (sessao == null || CollectionUtils.isEmpty(sessao.getVotos())) {
            return Collections.emptyList();
        }
        return sessao.getVotos().stream()
                .map(voto -> new VotoResponse(voto, voto.getAssociado()))
                .toList();
    }

    public PautaSessaoResponse fecharSessaoVotacao(Long pautaSessaoId) {
        Optional<PautaSessao> pautaSessaoOptional = pautaSessaoService.getRepository().findById(pautaSessaoId);

        if (pautaSessaoOptional.isEmpty()) {
            throw new ObjectNotFoundException("Sessão de votação não encontrada com o id: " + pautaSessaoId);
        }

        PautaSessao pautaSessao = pautaSessaoOptional.get();

        if (!StatusPauta.VOTACAO_FECHADA.equals(pautaSessao.getStatus())) {
            pautaSessao.setStatus(StatusPauta.VOTACAO_FECHADA);
            pautaSessaoService.getRepository().save(pautaSessao);
        }

        List<VotoResponse> votoResponses = recuperaVotos(pautaSessaoId);
        return new PautaSessaoResponse(pautaSessao.getPauta(), pautaSessao, votoResponses);
    }

    private List<VotoResponse> recuperaVotos(Long pautaSessaoId) {
        List<VotoSessao> lVotosSessao = votoSessaoService.getRepository().findAllByPautaSessaoId(pautaSessaoId);

        return lVotosSessao.stream()
                    .map(votoSessao -> new VotoResponse(votoSessao, votoSessao.getAssociado()))
                    .toList();
    }
}