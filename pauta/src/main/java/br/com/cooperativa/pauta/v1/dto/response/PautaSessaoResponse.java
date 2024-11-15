package br.com.cooperativa.pauta.v1.dto.response;

import br.com.cooperativa.pauta.v1.entity.Pauta;
import br.com.cooperativa.pauta.v1.entity.PautaSessao;
import br.com.cooperativa.pauta.v1.enums.StatusPauta;
import br.com.cooperativa.pauta.v1.enums.TipoVoto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PautaSessaoResponse {

    private PautaResponse pauta;

    private Long pautaSessaoId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inicio;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fim;

    private StatusPauta status;

    private Integer qntdVotosSim = 0;
    private Integer qntdVotosNao = 0;

    private List<VotoResponse> votos;

    public PautaSessaoResponse(Pauta pauta, PautaSessao pautaSessao, List<VotoResponse> votos) {
        this.pauta = new PautaResponse(pauta.getId(), pauta.getTitulo(), pauta.getDescricao());
        this.pautaSessaoId = pautaSessao.getId();
        this.inicio = pautaSessao.getDataInicio();
        this.fim = pautaSessao.getDataFim();
        this.status = pautaSessao.getStatus();
        this.votos = votos;

        if (!CollectionUtils.isEmpty(votos)) {
            this.qntdVotosSim = votos.stream()
                    .filter(umVoto -> TipoVoto.SIM.equals(umVoto.getTipoVoto()))
                    .toList().size();

            this.qntdVotosNao = votos.stream()
                    .filter(umVoto -> TipoVoto.NAO.equals(umVoto.getTipoVoto()))
                    .toList().size();
        }
    }

}
