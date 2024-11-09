package br.com.cooperativa.pauta.controller.v1;

import br.com.cooperativa.pauta.dto.AssociadoDTO;
import br.com.cooperativa.pauta.dto.AssociadoRecord;
import br.com.cooperativa.pauta.dto.PautaDTO;
import br.com.cooperativa.pauta.dto.VotoDTO;
import br.com.cooperativa.pauta.service.AssociadoService;
import br.com.cooperativa.pauta.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AssociadoControllerV1Impl implements AssociadoControllerV1 {

    @Autowired
    private AssociadoService associadoService;

    @Override
    public ResponseEntity<AssociadoRecord> cadastrarAssociado(AssociadoDTO associadoDTO) {
        return new ResponseEntity<>(associadoService.cadastrarAssociado(associadoDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AssociadoRecord>> recuperarTodosAssociados() {
        return new ResponseEntity<>(associadoService.recuperarTodosAssociados(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AssociadoRecord> recuperarAssociadoById(Long id) {
        return new ResponseEntity<>(associadoService.recuperarAssociadoById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AssociadoRecord> recuperarAssociadoByCpf(String cpf) {
        return new ResponseEntity<>(associadoService.recuperarAssociadoByCpf(cpf), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteAssociado(Long id) {
        return new ResponseEntity<>(associadoService.deleteAssociado(id), HttpStatus.NO_CONTENT);
    }
}
