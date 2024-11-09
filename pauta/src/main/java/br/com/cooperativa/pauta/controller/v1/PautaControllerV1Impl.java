package br.com.cooperativa.pauta.controller.v1;

import br.com.cooperativa.pauta.dto.*;
import br.com.cooperativa.pauta.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class PautaControllerV1Impl implements PautaControllerV1 {

    @Autowired
    private PautaService pautaService;

    @Override
    public ResponseEntity<PautaRecord> cadastrarPauta(PautaDTO pautaDTO) {
        pautaService.cadastrarPauta(pautaDTO);
        return null;
    }

    @Override
    public ResponseEntity<PautaResponse> abrirSessaoVotacao(Long id, int duracaoSegundos) {
        pautaService.abrirSessaoVotacao(id, duracaoSegundos);
        return null;
    }

    @Override
    public ResponseEntity<PautaResponse> votar(Long id, VotoDTO votoDTO) {
        pautaService.registrarVoto(id, votoDTO);
        return null;
    }

    @Override
    public ResponseEntity<ResultadoVotacao> resultado(Long pautaSessaoid) {
        pautaService.contabilizarResultado(pautaSessaoid);
        return null;
    }

}
