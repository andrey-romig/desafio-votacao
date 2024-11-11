package br.com.cooperativa.pauta.v1.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AssociadoRequest(
        @NotBlank(message = "Nome é obrigatório.")
        String nome,

        @NotBlank(message = "CPF é obrigatório.")
        String cpf
) {}