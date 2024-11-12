package br.com.cooperativa.pauta.v1.service;

import br.com.cooperativa.pauta.v1.base.BaseService;
import br.com.cooperativa.pauta.v1.dto.request.AssociadoRequest;
import br.com.cooperativa.pauta.v1.dto.response.AssociadoResponse;
import br.com.cooperativa.pauta.v1.entity.Associado;
import br.com.cooperativa.pauta.v1.exception.CPFValidationException;
import br.com.cooperativa.pauta.v1.exception.RegraNegocioException;
import br.com.cooperativa.pauta.v1.repository.AssociadoRepository;
import br.com.cooperativa.client.ClientValidadorCPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssociadoService extends BaseService<Associado, AssociadoRepository> {

    @Autowired
    private ClientValidadorCPF cpfValidationClient;

    public AssociadoResponse cadastrarAssociado(AssociadoRequest associadoDTO) {
        boolean isCpfDuplicado = getRepository().findByCpf(associadoDTO.cpf()).isPresent();
        if (isCpfDuplicado) {
            throw new RegraNegocioException("Já existe um associado com este CPF.");
        }

        boolean isCpfValido = cpfValidationClient.isCpfValido(associadoDTO.cpf());
        if (!isCpfValido) {
            throw new CPFValidationException("CPF inválido.");
        }

        Associado associado = new Associado();
        associado.setNome(associadoDTO.nome());
        associado.setCpf(associadoDTO.cpf());

        associado = getRepository().save(associado);

        return new AssociadoResponse(associado.getId(), associado.getNome(), associado.getCpf());
    }
}
