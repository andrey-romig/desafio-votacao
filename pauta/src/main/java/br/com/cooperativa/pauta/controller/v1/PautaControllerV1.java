package br.com.cooperativa.pauta.controller.v1;

import br.com.cooperativa.pauta.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/pauta")
public interface PautaControllerV1 {

    @Operation(summary = "Cadastrar uma nova pauta",
            description = "Permite cadastrar uma nova pauta")
    @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro ao criar a pauta, um ou mais dados podem estar inválidos")
    @PostMapping
    ResponseEntity<PautaRecord> cadastrarPauta(@RequestBody PautaDTO pautaDTO);

    @Operation(summary = "Abrir sessão de votação",
            description = "Abre uma sessão de votação para a pauta informada")
    @ApiResponse(responseCode = "200", description = "Sessão de votação aberta")
    @ApiResponse(responseCode = "404", description = "Pauta não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro ao abrir a sessão de votação, um ou mais dados podem estar inválidos")
    @PutMapping("/{id}/abrir")
    ResponseEntity<PautaResponse> abrirSessaoVotacao(@PathVariable("id") Long pautaId, @RequestParam(value = "duracaoSegundos", defaultValue = "60") int duracaoSegundos);

    @Operation(summary = "Receber votos",
            description = "Recebe o voto de um associado para a pauta informada")
    @ApiResponse(responseCode = "200", description = "Voto registrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Sessão de votação não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro ao registrar o voto, voto não registrado.")
    @PostMapping("/{id}/votar")
    ResponseEntity<PautaResponse> votar(@PathVariable("id") Long pautaSessaoid, @RequestBody VotoDTO votoDTO);

    @Operation(summary = "Contabilizar votos e mostrar resultado",
            description = "Contabiliza os votos e retorna o resultado da votação da pauta")
    @ApiResponse(responseCode = "200", description = "Resultado da votação")
    @ApiResponse(responseCode = "404", description = "Sessão de votação não encontrada")
    @GetMapping("/{id}/resultado")
    ResponseEntity<ResultadoVotacao> resultado(@PathVariable("id") Long pautaSessaoid);

}
