package br.com.cooperativa.pauta.v1.service;

import br.com.cooperativa.pauta.v1.base.BaseService;
import br.com.cooperativa.pauta.v1.dto.request.AssociadoRequest;
import br.com.cooperativa.pauta.v1.dto.response.AssociadoResponse;
import br.com.cooperativa.pauta.v1.entity.Associado;
import br.com.cooperativa.pauta.v1.repository.AssociadoRepository;
import org.springframework.stereotype.Service;

@Service
public class AssociadoService extends BaseService<Associado, AssociadoRepository> {


    public AssociadoResponse cadastrarAssociado(AssociadoRequest associadoDTO) {

        return null;
    }
}
