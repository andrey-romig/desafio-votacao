package br.com.cooperativa.pauta.v1.service;

import br.com.cooperativa.client.ClientValidadorCPF;
import br.com.cooperativa.pauta.v1.dto.request.AssociadoRequest;
import br.com.cooperativa.pauta.v1.entity.Associado;
import br.com.cooperativa.pauta.v1.exception.CPFValidationException;
import br.com.cooperativa.pauta.v1.exception.RegraNegocioException;
import br.com.cooperativa.pauta.v1.repository.AssociadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AssociadoServiceTest {

    @Mock
    private AssociadoRepository associadoRepository;

    @Mock
    private ClientValidadorCPF clientValidadorCPF;

    @InjectMocks
    private AssociadoService associadoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidarECriarAssociadoComCpfValido() {
        String cpf = "12345678901";
        AssociadoRequest request = new AssociadoRequest("Teste", cpf);

        when(clientValidadorCPF.isCpfValido(cpf)).thenReturn(true);
        when(associadoRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        associadoService.cadastrarAssociado(request);

        verify(associadoRepository, times(1)).save(any(Associado.class));
    }

    @Test
    void testValidarECriarAssociadoComCpfInvalido() {
        // Dado: um CPF inválido
        String cpf = "12345678901";
        AssociadoRequest request = new AssociadoRequest("Teste", cpf);

        when(clientValidadorCPF.isCpfValido(cpf)).thenReturn(false);

        Exception exception = assertThrows(CPFValidationException.class, () -> {
            associadoService.cadastrarAssociado(request);
        });

        assertEquals("CPF inválido.", exception.getMessage());
        verify(associadoRepository, never()).save(any(Associado.class));
    }

    @Test
    void testValidarECriarAssociadoJaExistente() {
        // Dado: um CPF que já existe
        String cpf = "12345678901";
        AssociadoRequest request = new AssociadoRequest("Teste", cpf);

        Associado associadoExistente = new Associado();
        associadoExistente.setCpf(cpf);

        when(clientValidadorCPF.isCpfValido(cpf)).thenReturn(true);
        when(associadoRepository.findByCpf(cpf)).thenReturn(Optional.of(associadoExistente));

        Exception exception = assertThrows(RegraNegocioException.class, () -> {
            associadoService.cadastrarAssociado(request);
        });

        assertEquals("Já existe um associado com este CPF.", exception.getMessage());
        verify(associadoRepository, never()).save(any(Associado.class));
    }
}
