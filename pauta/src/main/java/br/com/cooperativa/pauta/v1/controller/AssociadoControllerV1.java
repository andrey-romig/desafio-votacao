package br.com.cooperativa.pauta.v1.controller;

import br.com.cooperativa.pauta.v1.dto.request.AssociadoRequest;
import br.com.cooperativa.pauta.v1.dto.response.AssociadoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/associado")
public interface AssociadoControllerV1 {

    @Operation(summary = "Cadastrar um novo associado",
            description = "Permite cadastrar um novo associado",
            requestBody = @RequestBody(
                    content = @Content(
                            schema = @Schema(
                                    implementation = AssociadoRequest.class
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Associado criado com sucesso",
                    content = @Content(
                            schema = @Schema(
                                    implementation = AssociadoResponse.class
                            )
                    )
    )
    @ApiResponse(responseCode = "500", description = "Erro ao criar o associado, um ou mais dados podem estar inválidos")
    @PostMapping
    ResponseEntity<AssociadoResponse> cadastrarAssociado(@RequestBody AssociadoRequest associadoDTO);

    @Operation(summary = "Recuperar todos os associados",
            description = "Recupera todos os associados")
    @ApiResponse(responseCode = "200", description = "Associados encontrados com sucesso",
            content = @Content(
                    schema = @Schema(
                            implementation = AssociadoResponse.class
                    )
            )
    )
    @ApiResponse(responseCode = "404", description = "Nenhum associado cadastrado")
    @GetMapping
    ResponseEntity<List<AssociadoResponse>> recuperarTodosAssociados();

    @Operation(summary = "Recuperar associado pelo ID",
            description = "Retorna o associado a partir do ID")
    @ApiResponse(responseCode = "200", description = "Associado encontrado com sucesso",
            content = @Content(
                    schema = @Schema(
                            implementation = AssociadoResponse.class
                    )
            )
    )
    @ApiResponse(responseCode = "404", description = "Associado não encontrado a partir do ID informado")
    @GetMapping("/{id}")
    ResponseEntity<AssociadoResponse> recuperarAssociadoById(@PathVariable("id") Long id);

    @Operation(summary = "Recuperar associado pelo CPF",
            description = "Retorna o associado a partir do CPF")
    @ApiResponse(responseCode = "200", description = "Associado encontrado com sucesso",
            content = @Content(
                    schema = @Schema(
                            implementation = AssociadoResponse.class
                    )
            )
    )
    @ApiResponse(responseCode = "404", description = "Associado não encontrado a partir do CPF informado")
    @GetMapping("/{cpf}")
    ResponseEntity<AssociadoResponse> recuperarAssociadoByCpf(@PathVariable("cpf") String cpf);

    @Operation(summary = "Deleter um associado pelo ID",
            description = "Deleta um associado a partir do ID informado")
    @ApiResponse(responseCode = "204", description = "Associado excluído com sucesso")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAssociado(@PathVariable("id") Long id);
}

