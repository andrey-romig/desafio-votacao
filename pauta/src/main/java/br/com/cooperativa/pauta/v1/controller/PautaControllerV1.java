package br.com.cooperativa.pauta.v1.controller;

import br.com.cooperativa.pauta.v1.dto.request.PautaRequest;
import br.com.cooperativa.pauta.v1.dto.request.VotoRequest;
import br.com.cooperativa.pauta.v1.dto.response.PautaAndSessoesResponse;
import br.com.cooperativa.pauta.v1.dto.response.PautaResponse;
import br.com.cooperativa.pauta.v1.dto.response.PautaSessaoResponse;
import br.com.cooperativa.pauta.v1.dto.response.VotoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/pauta")
public interface PautaControllerV1 {

    @Operation(summary = "Cadastrar uma nova pauta",
            description = "Permite cadastrar uma nova pauta",
            requestBody = @RequestBody(
                content = @Content(
                        schema = @Schema(
                                implementation = PautaRequest.class
                        )
                )
            )
    )
    @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro ao criar a pauta, um ou mais dados podem estar inválidos")
    @ApiResponse(responseCode = "400", description = "Erro ao criar a pauta, campos inválidos")
    @PostMapping
    ResponseEntity<PautaResponse> cadastrarPauta(@Valid @RequestBody PautaRequest pautaDTO);

    @Operation(summary = "Abrir sessão de votação",
            description = "Abre uma sessão de votação para a pauta informada"
    )
    @ApiResponse(responseCode = "200", description = "Sessão de votação aberta",
            content = @Content(schema = @Schema(implementation = PautaSessaoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Pauta não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro ao abrir a sessão de votação, um ou mais dados podem estar inválidos")
    @PostMapping("/{id}/abrir")
    ResponseEntity<PautaSessaoResponse> abrirSessaoVotacao(@PathVariable("id") Long pautaId,
                                                           @RequestParam(value = "duracao", defaultValue = "60") int duracaoSegundos);

    @Operation(summary = "Receber votos",
            description = "Recebe o voto de um associado para a pauta informada",
            requestBody = @RequestBody(
                    content = @Content(
                            schema = @Schema(
                                    implementation = VotoRequest.class
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Voto registrado com sucesso",
            content = @Content(schema = @Schema(implementation = VotoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Sessão de votação não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro ao registrar o voto, voto não registrado.")
    @PostMapping("/{id}/votar")
    ResponseEntity<VotoResponse> votar(@PathVariable("id") Long pautaSessaoid, @RequestBody VotoRequest votoDTO);

    @Operation(summary = "Contabilizar votos e mostrar resultado",
            description = "Contabiliza os votos e retorna o resultado da votação da pauta")
    @ApiResponse(responseCode = "200", description = "Resultado da votação",
            content = @Content(schema = @Schema(implementation = PautaSessaoResponse.class))
    )
    @ApiResponse(responseCode = "404", description = "Sessão de votação não encontrada")
    @GetMapping("/{id}/resultado")
    ResponseEntity<PautaSessaoResponse> resultado(@PathVariable("id") Long pautaSessaoid);

    @Operation(summary = "Recuperar todas as pautas",
            description = "Recupera todas as pautas")
    @ApiResponse(responseCode = "200", description = "Pautas encontradas com sucesso",
        content = @Content(schema = @Schema(implementation = PautaResponse.class))
    )
    @ApiResponse(responseCode = "404", description = "Nenhuma pauta cadastrada")
    @GetMapping
    ResponseEntity<List<PautaResponse>> recuperarTodasPautas();

    @Operation(summary = "Recuperar pauta pelo ID",
            description = "Retorna a pauta a partir do ID")
    @ApiResponse(responseCode = "200", description = "Pauta encontrada com sucesso",
        content = @Content(schema = @Schema(implementation = PautaResponse.class))
    )
    @ApiResponse(responseCode = "404", description = "Pauta não encontrada a partir do ID informado")
    @GetMapping("/{id}")
    ResponseEntity<PautaResponse> recuperarPautaById(@PathVariable("id") Long id);

    @Operation(summary = "Recuperar todas as sessões de votação",
            description = "Recupera todas as sessões de votação")
    @ApiResponse(responseCode = "200", description = "Sessões encontradas com sucesso",
            content = @Content(schema = @Schema(implementation = PautaAndSessoesResponse.class))
    )
    @ApiResponse(responseCode = "404", description = "Nenhuma pauta com sessão cadastrada")
    @GetMapping
    ResponseEntity<List<PautaAndSessoesResponse>> recuperarSessoes();

}
