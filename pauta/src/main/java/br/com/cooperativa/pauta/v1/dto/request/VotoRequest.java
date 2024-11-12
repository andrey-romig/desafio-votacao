package br.com.cooperativa.pauta.v1.dto.request;

import br.com.cooperativa.pauta.v1.enums.TipoVoto;
import jakarta.validation.constraints.NotNull;

public record VotoRequest(
        @NotNull(message = "ID do associado é obrigatório.")
        Long associadoId,

        @NotNull(message = "Voto é obrigatório.")
        TipoVoto tipoVoto
) {}
