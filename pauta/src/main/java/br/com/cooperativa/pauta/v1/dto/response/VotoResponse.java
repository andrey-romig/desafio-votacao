package br.com.cooperativa.pauta.v1.dto.response;

import br.com.cooperativa.pauta.v1.entity.Associado;
import br.com.cooperativa.pauta.v1.entity.VotoSessao;
import br.com.cooperativa.pauta.v1.enums.TipoVoto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotoResponse {

    private AssociadoResponse associado;
    private TipoVoto tipoVoto;

    public VotoResponse(VotoSessao votoSessao, Associado associado) {
        this.associado = new AssociadoResponse(associado.getNome(), associado.getCpf());
        this.tipoVoto = votoSessao.getVoto();
    }

}
