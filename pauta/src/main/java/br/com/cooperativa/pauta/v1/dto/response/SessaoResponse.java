package br.com.cooperativa.pauta.v1.dto.response;

import br.com.cooperativa.pauta.v1.entity.PautaSessao;
import br.com.cooperativa.pauta.v1.enums.StatusPauta;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoResponse {

    private Long pautaSessaoId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inicio;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fim;

    private StatusPauta status;
    private List<VotoResponse> votos;

    public SessaoResponse(PautaSessao pautaSessao, List<VotoResponse> votos) {
        this.pautaSessaoId = pautaSessao.getId();
        this.inicio = pautaSessao.getDataInicio();
        this.fim = pautaSessao.getDataFim();
        this.status = pautaSessao.getStatus();
        this.votos = votos;
    }
}
