package br.com.cooperativa.pauta.controller.v1;

import br.com.cooperativa.pauta.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/associado")
public interface AssociadoControllerV1 {

    @Operation(summary = "Cadastrar um novo associado",
            description = "Permite cadastrar um novo associado")
    @ApiResponse(responseCode = "201", description = "Associado criado com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro ao criar o associado, um ou mais dados podem estar inválidos")
    @PostMapping
    ResponseEntity<AssociadoRecord> cadastrarAssociado(@RequestBody AssociadoDTO associadoDTO);

    @Operation(summary = "Recuperar todos os associados",
            description = "Recupera todos os associados")
    @ApiResponse(responseCode = "200", description = "Associados encontrados com sucesso")
    @ApiResponse(responseCode = "404", description = "Nenhum associado cadastrado")
    @GetMapping
    ResponseEntity<List<AssociadoRecord>> recuperarTodosAssociados();

    @Operation(summary = "Recuperar associado pelo ID",
            description = "Retorna o associado a partir do ID")
    @ApiResponse(responseCode = "200", description = "Associado encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Associado não encontrado a partir do ID informado")
    @GetMapping("/{id}")
    ResponseEntity<AssociadoRecord> recuperarAssociadoById(@PathVariable("id") Long id);

    @Operation(summary = "Recuperar associado pelo CPF",
            description = "Retorna o associado a partir do CPF")
    @ApiResponse(responseCode = "200", description = "Associado encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Associado não encontrado a partir do CPF informado")
    @GetMapping("/{cpf}")
    ResponseEntity<AssociadoRecord> recuperarAssociadoByCpf(@PathVariable("cpf") String cpf);

    @Operation(summary = "Deleter um associado pelo ID",
            description = "Deleta um associado a partir do ID informado")
    @ApiResponse(responseCode = "204", description = "Associado excluído com sucesso")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAssociado(@PathVariable("id") Long id);
}

