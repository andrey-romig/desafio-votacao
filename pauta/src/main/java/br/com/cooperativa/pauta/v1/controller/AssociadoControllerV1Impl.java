package br.com.cooperativa.pauta.v1.controller;

import br.com.cooperativa.pauta.v1.dto.request.AssociadoRequest;
import br.com.cooperativa.pauta.v1.dto.response.AssociadoResponse;
import br.com.cooperativa.pauta.v1.entity.Associado;
import br.com.cooperativa.pauta.v1.service.AssociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Controller
public class AssociadoControllerV1Impl implements AssociadoControllerV1 {

    @Autowired
    private AssociadoService associadoService;

    @Override
    public ResponseEntity<AssociadoResponse> cadastrarAssociado(AssociadoRequest associadoDTO) {
        AssociadoResponse associadoResponse = associadoService.cadastrarAssociado(associadoDTO);
        return Objects.nonNull(associadoResponse) ? ResponseEntity.ok(associadoResponse) : ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<List<AssociadoResponse>> recuperarTodosAssociados() {
        List<Associado> associados = associadoService.getRepository().findAll();

        if (CollectionUtils.isEmpty(associados)) {
            return ResponseEntity.notFound().build();
        }

        List<AssociadoResponse> associadosResponse = associados.stream()
                .map(associado -> new AssociadoResponse(associado.getId(), associado.getNome(), associado.getCpf()))
                .toList();

        return ResponseEntity.ok(associadosResponse);
    }

    @Override
    public ResponseEntity<AssociadoResponse> recuperarAssociadoById(Long id) {
        Associado associado = associadoService.getRepository().findById(id).orElse(null);

        if (Objects.isNull(associado)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new AssociadoResponse(associado.getId(), associado.getNome(), associado.getCpf()));
    }

    @Override
    public ResponseEntity<AssociadoResponse> recuperarAssociadoByCpf(String cpf) {
        Associado associado = associadoService.getRepository().findByCpf(cpf).orElse(null);

        if (Objects.isNull(associado)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new AssociadoResponse(associado.getId(), associado.getNome(), associado.getCpf()));    }

    @Override
    public ResponseEntity<Void> deleteAssociado(Long id) {
        associadoService.getRepository().deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
