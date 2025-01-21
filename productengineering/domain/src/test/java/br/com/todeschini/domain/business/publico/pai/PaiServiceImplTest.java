package br.com.todeschini.domain.business.publico.pai;

import br.com.todeschini.domain.ConversaoValores;
import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.business.processadores.MaterialProcessadorFactory;
import br.com.todeschini.domain.business.publico.acessorio.api.AcessorioService;
import br.com.todeschini.domain.business.publico.acessoriousado.api.AcessorioUsadoService;
import br.com.todeschini.domain.business.publico.categoriacomponente.api.CategoriaComponenteService;
import br.com.todeschini.domain.business.publico.cor.api.CorService;
import br.com.todeschini.domain.business.publico.filho.api.FilhoService;
import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.business.publico.maquina.api.MaquinaService;
import br.com.todeschini.domain.business.publico.material.api.MaterialService;
import br.com.todeschini.domain.business.publico.medidas.api.MedidasService;
import br.com.todeschini.domain.business.publico.modelo.api.ModeloService;
import br.com.todeschini.domain.business.publico.pai.spi.CrudPai;
import br.com.todeschini.domain.business.publico.roteiro.api.RoteiroService;
import br.com.todeschini.domain.business.publico.roteiromaquina.api.RoteiroMaquinaService;
import br.com.todeschini.domain.exceptions.BadRequestException;
import br.com.todeschini.domain.exceptions.RegistroDuplicadoException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.domain.util.tests.PaiFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaiServiceImplTest {

    private CrudPai crud;
    private ConversaoValores conversaoValores;
    private ModeloService modeloService;
    private CategoriaComponenteService categoriaComponenteService;
    private FilhoService filhoService;
    private MedidasService medidasService;
    private MaterialService materialService;
    private RoteiroService roteiroService;
    private MaquinaService maquinaService;
    private RoteiroMaquinaService roteiroMaquinaService;
    private CorService corService;
    private AcessorioService acessorioService;
    private AcessorioUsadoService acessorioUsadoService;
    private MaterialProcessadorFactory materialProcessadorFactory;
    private PaiServiceImpl service;

    @BeforeEach
    void setup() {
        crud = mock(CrudPai.class);
        conversaoValores = mock(ConversaoValores.class);
        modeloService = mock(ModeloService.class);
        categoriaComponenteService = mock(CategoriaComponenteService.class);
        filhoService = mock(FilhoService.class);
        medidasService = mock(MedidasService.class);
        materialService = mock(MaterialService.class);
        roteiroService = mock(RoteiroService.class);
        maquinaService = mock(MaquinaService.class);
        roteiroMaquinaService = mock(RoteiroMaquinaService.class);
        corService = mock(CorService.class);
        acessorioService = mock(AcessorioService.class);
        acessorioUsadoService = mock(AcessorioUsadoService.class);
        materialProcessadorFactory = mock(MaterialProcessadorFactory.class);
        service = new PaiServiceImpl(crud, conversaoValores, modeloService, categoriaComponenteService, filhoService, medidasService,
                materialService, roteiroService, maquinaService, roteiroMaquinaService, corService, acessorioService, acessorioUsadoService, materialProcessadorFactory);
    }

    @Test
    void deveBuscarTodos() {
        PageableRequest request = new PageableRequest(1,1,null,null,null,null,null);
        Paged<DPai> pagedResult = new Paged<>();
        when(crud.buscarTodos(request)).thenReturn(pagedResult);

        Paged<DPai> result = service.buscar(request);

        assertNotNull(result);
        verify(crud, times(1)).buscarTodos(request);
    }

    @Test
    void deveBuscarPorId() {
        DPai domain = PaiFactory.createDPai();
        when(crud.buscar(1)).thenReturn(domain);

        DPai result = service.buscar(domain.getCodigo());

        assertNotNull(result);
        verify(crud, times(1)).buscar(domain.getCodigo());
    }

    @Test
    void deveIncluir() {
        DPai domain = PaiFactory.createDPai();

        when(crud.pesquisarPorDescricao(domain.getDescricao())).thenReturn(List.of());
        when(crud.pesquisarPorDescricao(domain.getDescricao())).thenReturn(List.of());
        when(crud.inserir(domain)).thenReturn(domain);

        DPai result = service.incluir(domain);

        assertNotNull(result);
        verify(crud, times(1)).inserir(domain);
    }

    @Test
    void deveLancarExcecaoAoIncluirDuplicado() {
        DPai domain = PaiFactory.createDuplicatedDPai("Duplicado");
        when(crud.pesquisarPorDescricao("Duplicado"))
                .thenReturn(List.of(new DPai(1, null,null,"Duplicado", null,null ,null,null,null, null, null, null, null, null, null, null)));

        assertThrows(RegistroDuplicadoException.class, () -> service.incluir(domain));
        verify(crud, never()).inserir(any());
    }

    @Test
    void deveAtualizar() {
        DPai domain = PaiFactory.createDPai();

        when(crud.pesquisarPorDescricao(domain.getDescricao())).thenReturn(List.of());
        when(crud.atualizar(domain)).thenReturn(domain);

        DPai result = service.atualizar(domain);

        assertNotNull(result);
        verify(crud, times(1)).atualizar(domain);
    }

    @Test
    void deveLancarExcecaoSeAtributosEValoresNaoForemIguais() {
        List<Integer> codigos = List.of(1);
        List<String> atributos = List.of("descricao");
        List<Object> valores = List.of("Novo Valor", 10);

        assertThrows(BadRequestException.class, () ->
                service.atualizarEmLote(codigos, atributos, valores)
        );
    }

    @Test
    void deveAtualizarEmLoteComSucesso() throws NoSuchFieldException {
        List<Integer> codigos = List.of(1, 2);
        List<String> atributos = List.of("plasticoAdicional");
        List<Object> valores = List.of(10.0);

        DPai domain1 = PaiFactory.createDPai();
        DPai domain2 = PaiFactory.createDPai();
        domain2.setCodigo(2);

        Field updatedField = DPai.class.getDeclaredField("plasticoAdicional");

        when(crud.buscar(anyInt())).thenReturn(domain1, domain2);
        when(crud.atualizarEmLote(anyList())).thenReturn(List.of(domain1, domain2));
        when(conversaoValores.buscarCampoNaHierarquia(eq(DPai.class), eq("plasticoAdicional"))).thenReturn(updatedField);
        when(conversaoValores.convertValor(eq(Double.class), any())).thenAnswer(invocation -> invocation.getArgument(1));

        List<DPai> atualizados = service.atualizarEmLote(codigos, atributos, valores);

        assertNotNull(atualizados);
        assertEquals(2, atualizados.size());
        assertEquals(10.0, atualizados.get(0).getPlasticoAdicional());
        assertEquals(10.0, atualizados.get(1).getPlasticoAdicional());

        verify(crud, times(1)).atualizarEmLote(anyList());
    }

    @Test
    void deveLancarExcecaoQuandoTamanhosNaoForemIguais() {
        List<Integer> codigos = List.of(1, 2);
        List<String> atributos = List.of("descricao");
        List<Object> valores = List.of();

        assertThrows(BadRequestException.class, () -> service.atualizarEmLote(codigos, atributos, valores));
    }

    @Test
    void deveSubstituirPorVersaoAntigaComSucesso() {
        Integer id = 1;
        Integer versionId = 2;

        DPai versaoAntiga = new DPai(versionId, null,null,"Versão Antiga", null, null, null, null, null, null, null, null, null, null, null, null);
        when(crud.substituirPorVersaoAntiga(id, versionId)).thenReturn(versaoAntiga);

        DPai substituido = service.substituirPorVersaoAntiga(id, versionId);

        assertNotNull(substituido);
        assertEquals("Versão Antiga", substituido.getDescricao());
        assertEquals(versionId, substituido.getCodigo());

        verify(crud, times(1)).substituirPorVersaoAntiga(id, versionId);
    }

    @Test
    void deveLancarExcecaoQuandoVersaoNaoExistir() {
        Integer id = 1;
        Integer versionId = 99;

        when(crud.substituirPorVersaoAntiga(id, versionId)).thenThrow(new ResourceNotFoundException("Versão não encontrada"));

        assertThrows(ResourceNotFoundException.class, () -> service.substituirPorVersaoAntiga(id, versionId));

        verify(crud, times(1)).substituirPorVersaoAntiga(id, versionId);
    }

    @Test
    void deveBuscarHistorico() {
        List<DHistory<DPai>> historico = List.of();
        when(crud.buscarHistorico(1)).thenReturn(historico);

        List<DHistory<DPai>> result = service.buscarHistorico(1);

        assertNotNull(result);
        verify(crud, times(1)).buscarHistorico(1);
    }

    @Test
    void deveInativar() {
        doNothing().when(crud).inativar(1);

        service.inativar(1);

        verify(crud, times(1)).inativar(1);
    }

    @Test
    void deveExcluir() {
        doNothing().when(crud).remover(1);

        service.excluir(1);

        verify(crud, times(1)).remover(1);
    }
}
