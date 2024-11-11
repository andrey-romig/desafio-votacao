package br.com.cooperativa.pauta.v1.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PautaRequest(
        @NotBlank(message = "Titulo é obrigatório.")
        String titulo,

        @NotBlank(message = "Descricao é obrigatório.")
        String descricao
) {}
