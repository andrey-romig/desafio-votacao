package br.com.cooperativa.pauta.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociadoResponse {

    private Long id;
    private String associadoNome;
    private String associadoCpf;

}
