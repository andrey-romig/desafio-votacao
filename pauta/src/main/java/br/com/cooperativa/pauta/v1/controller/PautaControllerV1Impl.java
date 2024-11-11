package br.com.cooperativa.pauta.v1.controller;

import br.com.cooperativa.pauta.v1.dto.request.PautaRequest;
import br.com.cooperativa.pauta.v1.dto.request.VotoRequest;
import br.com.cooperativa.pauta.v1.dto.response.PautaAndSessoesResponse;
import br.com.cooperativa.pauta.v1.dto.response.PautaResponse;
import br.com.cooperativa.pauta.v1.dto.response.PautaSessaoResponse;
import br.com.cooperativa.pauta.v1.dto.response.VotoResponse;
import br.com.cooperativa.pauta.v1.entity.Pauta;
import br.com.cooperativa.pauta.v1.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
public class PautaControllerV1Impl implements PautaControllerV1 {

    @Autowired
    private PautaService pautaService;

    @Override
    public ResponseEntity<PautaResponse> cadastrarPauta(PautaRequest pautaDTO) {
        PautaResponse pautaResponse = pautaService.cadastrarPauta(pautaDTO);
        return Objects.nonNull(pautaResponse) ? ResponseEntity.ok(pautaResponse) : ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<PautaSessaoResponse> abrirSessaoVotacao(Long pautaId, int duracaoSegundos) {
        PautaSessaoResponse pautaSessaoResponse = pautaService.abrirSessaoVotacao(pautaId, duracaoSegundos);
        return Objects.nonNull(pautaSessaoResponse) ? ResponseEntity.ok(pautaSessaoResponse) : ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<VotoResponse> votar(Long pautaSessaoid, VotoRequest votoDTO) {
        VotoResponse votoResponse = pautaService.registrarVoto(pautaSessaoid, votoDTO);
        return Objects.nonNull(votoResponse) ? ResponseEntity.ok(votoResponse) : ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<PautaSessaoResponse> resultado(Long pautaSessaoid) {
        PautaSessaoResponse pautaSessaoResponse = pautaService.contabilizarResultado(pautaSessaoid);
        return Objects.nonNull(pautaSessaoResponse) ? ResponseEntity.ok(pautaSessaoResponse) : ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<List<PautaResponse>> recuperarTodasPautas() {
        List<Pauta> pautas = pautaService.getRepository().findAll();

        if (CollectionUtils.isEmpty(pautas)) {
            return ResponseEntity.notFound().build();
        }

        List<PautaResponse> pautasResponse = pautas.stream()
                .map(pauta -> new PautaResponse(pauta.getTitulo(), pauta.getDescricao()))
                .toList();

        return ResponseEntity.ok(pautasResponse);
    }

    @Override
    public ResponseEntity<PautaResponse> recuperarPautaById(Long id) {
        Pauta pauta = pautaService.getRepository().findById(id).orElse(null);

        if (Objects.isNull(pauta)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new PautaResponse(pauta.getTitulo(), pauta.getDescricao()));
    }

    @Override
    public ResponseEntity<List<PautaAndSessoesResponse>> recuperarSessoes() {
        List<PautaAndSessoesResponse> sessoes = pautaService.recuperarSessoes();

        if (CollectionUtils.isEmpty(sessoes)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(sessoes);
    }

}
