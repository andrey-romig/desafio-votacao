package br.com.cooperativa.pauta.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PautaAndSessoesResponse {

    private PautaResponse pauta;
    private List<SessaoResponse> sessoes;

}
