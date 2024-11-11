package br.com.cooperativa.pauta.v1.dto.response;

import br.com.cooperativa.pauta.v1.entity.Pauta;
import br.com.cooperativa.pauta.v1.entity.PautaSessao;
import br.com.cooperativa.pauta.v1.enums.StatusPauta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PautaSessaoResponse {

    private PautaResponse pauta;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private StatusPauta status;
    private List<VotoResponse> votos;

    public PautaSessaoResponse(Pauta pauta, PautaSessao pautaSessao, List<VotoResponse> votos) {
        this.pauta = new PautaResponse(pauta.getTitulo(), pauta.getDescricao());
        this.inicio = pautaSessao.getDataInicio();
        this.fim = pautaSessao.getDataFim();
        this.status = pautaSessao.getStatus();
        this.votos = votos;
    }

}
