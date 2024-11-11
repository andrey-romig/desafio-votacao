package br.com.cooperativa.pauta.v1.dto.response;

import br.com.cooperativa.pauta.v1.entity.Pauta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PautaResponse {

    private String pautaTitulo;
    private String pautaDescricao;

    public PautaResponse(Pauta pauta) {
        this.pautaTitulo = pauta.getTitulo();
        this.pautaDescricao = pauta.getDescricao();
    }

}
