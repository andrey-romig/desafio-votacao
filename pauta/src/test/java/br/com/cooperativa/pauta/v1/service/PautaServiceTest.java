package br.com.cooperativa.pauta.v1.service;

import br.com.cooperativa.pauta.main.PautaApplication;
import br.com.cooperativa.pauta.v1.dto.response.PautaResponse;
import br.com.cooperativa.pauta.v1.dto.response.PautaSessaoResponse;
import br.com.cooperativa.pauta.v1.entity.Pauta;
import br.com.cooperativa.pauta.v1.entity.PautaSessao;
import br.com.cooperativa.pauta.v1.enums.StatusPauta;
import br.com.cooperativa.pauta.v1.enums.TipoVoto;
import br.com.cooperativa.pauta.v1.repository.PautaRepository;
import br.com.cooperativa.pauta.v1.repository.PautaSessaoRepository;
import br.com.cooperativa.pauta.v1.repository.VotoSessaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private PautaSessaoRepository pautaSessaoRepository;

    @Mock
    private VotoSessaoRepository votoSessaoRepository;

    @InjectMocks
    private PautaService pautaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAbrirNovaSessao() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);

        PautaSessao pautaSessao = new PautaSessao();
        pautaSessao.setId(1L);
        pautaSessao.setStatus(StatusPauta.VOTACAO_ABERTA);
        pautaSessao.setPauta(pauta);

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
        when(pautaSessaoRepository.save(any(PautaSessao.class))).thenReturn(pautaSessao);

        PautaSessaoResponse response = pautaService.abrirSessaoVotacao(1L, 30);

        assertEquals(StatusPauta.VOTACAO_ABERTA, response.getStatus());
        verify(pautaSessaoRepository, times(1)).save(any(PautaSessao.class));
    }

    @Test
    void testContarVotosSessao() {
        Long pautaSessaoId = 1L;
        when(votoSessaoRepository.countByPautaSessaoIdAndVoto(anyLong(), eq(TipoVoto.SIM))).thenReturn(10L);
        when(votoSessaoRepository.countByPautaSessaoIdAndVoto(anyLong(), eq(TipoVoto.NAO))).thenReturn(5L);

        PautaSessaoResponse response = pautaService.contabilizarResultado(pautaSessaoId);

        assertEquals(10, response.getQntdVotosSim());
        assertEquals(5, response.getQntdVotosNao());
    }
}
