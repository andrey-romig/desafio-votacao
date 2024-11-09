package br.com.cooperativa.pauta.dto;

import br.com.cooperativa.pauta.enums.TipoVoto;
import lombok.Data;

@Data
public class VotoDTO {
    private Long associadoId;
    private TipoVoto voto;

}
