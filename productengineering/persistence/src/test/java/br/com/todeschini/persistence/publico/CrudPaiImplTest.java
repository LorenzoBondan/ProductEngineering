package br.com.todeschini.persistence.publico;

import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.business.enums.DSituacaoEnum;
import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.business.publico.history.api.HistoryService;
import br.com.todeschini.domain.business.publico.pai.DPai;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.SituacaoEnum;
import br.com.todeschini.persistence.entities.publico.Pai;
import br.com.todeschini.persistence.filters.SituacaoFilter;
import br.com.todeschini.persistence.publico.pai.CrudPaiImpl;
import br.com.todeschini.persistence.publico.pai.PaiDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.pai.PaiQueryRepository;
import br.com.todeschini.persistence.publico.pai.PaiRepository;
import br.com.todeschini.persistence.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CrudPaiImplTest {

    @Mock
    private PaiRepository repository;
    @Mock
    private PaiQueryRepository queryRepository;
    @Mock
    private PaiDomainToEntityAdapter adapter;
    @Mock
    private EntityService entityService;
    @Mock
    private PageRequestUtils pageRequestUtils;
    @Mock
    private HistoryService historyService;
    @Mock
    private SituacaoFilter<Pai> situacaoFilter;
    @Mock
    private AuditoriaService auditoriaService;
    private CrudPaiImpl crudPai;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        crudPai = new CrudPaiImpl(
                repository,
                queryRepository,
                adapter,
                entityService,
                pageRequestUtils,
                historyService,
                situacaoFilter,
                auditoriaService
        );
    }

    @Test
    public void testBuscarTodos() {
        // Arrange
        PageableRequest request = new PageableRequest();
        request.setColunas(List.of("descricao"));
        request.setOperacoes(List.of("="));
        request.setValores(List.of("Pai A"));
        request.setSort(List.of("codigo;a").toArray(new String[0]));
        request.setPage(0);
        request.setPageSize(10);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("descricao").ascending());

        Pai entity = new Pai();
        entity.setDescricao("Pai A");

        DPai domain = new DPai();
        domain.setDescricao("Pai A");

        SpecificationHelper<Pai> helper = new SpecificationHelper<>();
        Specification<Pai> specification = helper.buildSpecification(request.getColunas(), request.getOperacoes(), request.getValores());
        Page<Pai> PaiPage = new PageImpl<>(List.of(entity), pageRequest, 1);

        when(pageRequestUtils.toPage(request)).thenReturn(pageRequest);
        when(queryRepository.findAll(any(Specification.class), eq(pageRequest))).thenReturn(PaiPage);
        when(adapter.toDomain(entity)).thenReturn(domain);
        when(situacaoFilter.addExcludeSituacaoLixeira(any(Specification.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Paged<DPai> result = crudPai.buscarTodos(request);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getNumberOfElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getContent().size());
        assertEquals("Pai A", result.getContent().getFirst().getDescricao());

        verify(pageRequestUtils, times(1)).toPage(request);
        verify(queryRepository, times(1)).findAll(any(Specification.class), eq(pageRequest));
        verify(adapter, times(1)).toDomain(entity);
        verify(situacaoFilter, times(1)).addExcludeSituacaoLixeira(any(Specification.class));
    }

    @Test
    public void testPesquisarPorDescricao() {
        // Arrange
        String nome = "Objeto 1";
        Pai entity = new Pai();
        entity.setDescricao(nome);
        when(queryRepository.findByDescricaoIgnoreCase(nome)).thenReturn(List.of(entity));

        // Act
        Collection<? extends DPai> result = crudPai.pesquisarPorDescricao(nome);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testBuscar() {
        // Arrange
        Integer id = 1;
        Pai entity = new Pai();
        entity.setCdpai(id);

        // Mock do adaptador para converter a entidade em domain
        DPai domain = new DPai();
        domain.setCodigo(id);
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(adapter.toDomain(entity)).thenReturn(domain);  // Simula a conversão da entidade para o domínio

        // Act
        DPai result = crudPai.buscar(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getCodigo());
        verify(repository, times(1)).findById(id);  // Verifica se o repositório foi chamado
        verify(adapter, times(1)).toDomain(entity); // Verifica se o adaptador foi chamado
    }

    @Test
    public void testBuscar_ThrowResourceNotFoundException() {
        // Arrange
        Integer id = 999;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> crudPai.buscar(id));
    }

    @Test
    public void testBuscarHistorico() {
        // Arrange
        Integer id = 1;
        Pai entity = new Pai();
        entity.setCdpai(id);
        DHistory<Pai> history = new DHistory<>(1, null, "author", entity, null);
        when(historyService.getHistoryEntityByRecord(Pai.class, "tb_pai", id.toString(), AttributeMappings.PAI.getMappings()))
                .thenReturn(List.of(history));

        // Act
        List<DHistory<DPai>> result = crudPai.buscarHistorico(id);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("author", result.getFirst().getAuthor());
    }

    @Test
    public void testBuscarAtributosEditaveisEmLote() {
        // Arrange
        List<String> atributos = List.of("modelo", "categoriaComponente", "bordasComprimento", "bordasLargura", "numeroCantoneiras", "tntUmaFace", "plasticoAcima", "plasticoAdicional", "larguraPlastico", "faces", "especial", "tipoPintura");
        when(entityService.obterAtributosEditaveis(DPai.class)).thenReturn(atributos);

        // Act
        List<String> result = crudPai.buscarAtributosEditaveisEmLote();

        // Assert
        assertNotNull(result);
        assertEquals(result, atributos);
        assertEquals(12, result.size());
    }

    @Test
    public void testInserir() {
        // Arrange
        DPai domain = new DPai();
        Pai entity = new Pai();

        // Mock do adapter.toEntity e repository.save
        when(adapter.toEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);

        // Mock do adapter.toDomain para garantir que o retorno de toDomain não seja null
        when(adapter.toDomain(entity)).thenReturn(domain);

        // Act
        DPai result = crudPai.inserir(domain);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).save(entity);
        verify(adapter, times(1)).toDomain(entity);  // Verificando se o adapter foi chamado corretamente
    }

    @Test
    public void testAtualizar() {
        // Arrange
        DPai domain = new DPai();
        domain.setCodigo(1);
        Pai entity = new Pai();
        entity.setCdpai(1);

        // Mock do repositório
        when(repository.existsById(domain.getCodigo())).thenReturn(true); // Garantir que a entidade existe
        when(adapter.toEntity(domain)).thenReturn(entity); // Converter de domain para entidade
        when(repository.save(entity)).thenReturn(entity); // Simula o save do repositório

        // Mock do entityService
        doNothing().when(entityService).verifyDependenciesStatus(entity); // Garantir que o método de verificação não faça nada

        // Mock do auditoriaService
        doNothing().when(auditoriaService).setCreationProperties(entity); // Garantir que o método de auditoria não faça nada

        // Mock do adapter.toDomain()
        when(adapter.toDomain(entity)).thenReturn(domain); // Converter de entidade de volta para domain

        // Act
        DPai result = crudPai.atualizar(domain); // Chama o método de atualização

        // Assert
        assertNotNull(result); // Verifica se o retorno não é nulo
        verify(auditoriaService, times(1)).setCreationProperties(entity); // Verifica se a auditoria foi chamada Pairetamente
        verify(repository, times(1)).save(entity); // Verifica se o repositório foi chamado para salvar
        verify(adapter, times(1)).toDomain(entity); // Verifica se a conversão de volta para domain foi chamada
    }

    @Test
    public void testAtualizar_ThrowResourceNotFoundException() {
        // Arrange
        DPai domain = new DPai();
        domain.setCodigo(99999);
        when(repository.existsById(domain.getCodigo())).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> crudPai.atualizar(domain));
    }

    @Test
    public void testAtualizarEmLote() {
        // Arrange
        List<DPai> list = List.of(new DPai());
        when(repository.saveAll(anyList())).thenReturn(Collections.emptyList());

        // Act
        List<DPai> result = crudPai.atualizarEmLote(list);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testAtualizar_DeveChamarSetCreationPropertiesEManterAtributosAuditaveis() {
        // Arrange
        DPai domain = new DPai();
        domain.setCodigo(1); // Exemplo de código para a entidade
        domain.setSituacao(DSituacaoEnum.ATIVO); // Situacao inicial

        Pai entity = new Pai();
        entity.setCdpai(1);
        entity.setSituacao(SituacaoEnum.ATIVO);
        entity.setCriadoem(LocalDateTime.now().minusDays(1));  // Valor de criadoem pré-existente
        entity.setCriadopor("user123");  // Valor de criadopor pré-existente

        when(repository.existsById(domain.getCodigo())).thenReturn(true);
        when(adapter.toEntity(domain)).thenReturn(entity);

        // Act
        crudPai.atualizar(domain);

        // Assert
        // Verificar se o método setCreationProperties foi chamado uma vez
        verify(auditoriaService, times(1)).setCreationProperties(entity);

        // Verificar se os valores dos campos auditáveis não foram alterados
        assertEquals("user123", entity.getCriadopor(), "O campo criadopor não foi mantido.");
        assertNotNull(entity.getCriadoem(), "O campo criadoem não foi mantido.");
        assertEquals(SituacaoEnum.ATIVO, entity.getSituacao(), "O campo situacao não foi mantido.");
    }

    @Test
    public void testAtualizar_DeveLancarExceptionQuandoNaoExistir() {
        // Arrange
        DPai domain = new DPai();
        domain.setCodigo(99999); // Código que não existe

        when(repository.existsById(domain.getCodigo())).thenReturn(false);

        // Act & Assert
        // Verificar se a exceção ResourceNotFoundException é lançada
        try {
            crudPai.atualizar(domain);
        } catch (Exception e) {
            assertInstanceOf(ResourceNotFoundException.class, e);
        }
    }

    @Test
    public void testSubstituirPorVersaoAntiga() {
        // Arrange
        Integer id = 1;
        Integer versionId = 1;
        Pai oldVersion = new Pai();
        oldVersion.setCdpai(1); // Defina o código da entidade
        DHistory<Pai> history = new DHistory<>(versionId, null, "author", oldVersion, null); // Simula o histórico com a versão 1

        // Mock do historyService para retornar o histórico Paireto
        when(historyService.getHistoryEntityByRecord(Pai.class, "tb_pai", id.toString(), AttributeMappings.PAI.getMappings()))
                .thenReturn(List.of(history)); // Retorna uma lista com o histórico que tem o versionId

        // Mock do adapter.toDomain para converter a entidade em domain
        DPai domain = new DPai();
        domain.setDescricao("author");
        when(adapter.toDomain(oldVersion)).thenReturn(domain);

        // Mock do repository.save para salvar a entidade
        when(repository.save(oldVersion)).thenReturn(oldVersion); // Simula o save do repositório

        // Act
        DPai result = crudPai.substituirPorVersaoAntiga(id, versionId);

        // Assert
        assertNotNull(result); // Verifica se o resultado não é nulo
        assertEquals("author", result.getDescricao()); // Verifica se o nome do domain é igual ao esperado
        verify(historyService, times(1)).getHistoryEntityByRecord(Pai.class, "tb_pai", id.toString(), AttributeMappings.PAI.getMappings()); // Verifica se o histórico foi buscado
        verify(adapter, times(1)).toDomain(oldVersion); // Verifica se a conversão de entidade para domain foi chamada
        verify(repository, times(1)).save(oldVersion); // Verifica se o repositório foi chamado para salvar
    }

    @Test
    public void testInativar() {
        // Arrange
        Integer id = 1;
        Pai entity = new Pai();
        entity.setCdpai(id);
        entity.setSituacao(SituacaoEnum.ATIVO);
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        crudPai.inativar(id);

        // Assert
        assertEquals(SituacaoEnum.INATIVO, entity.getSituacao());
        verify(repository, times(1)).save(entity);
    }

    @Test
    public void testRemover() {
        // Arrange
        Integer id = 1;
        Pai entity = new Pai();
        entity.setCdpai(id);
        entity.setSituacao(SituacaoEnum.ATIVO); // Defina um status inicial para a entidade
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        // Simula a mudança de status para LIXEIRA
        doAnswer(invocation -> {
            Pai t = invocation.getArgument(0);
            t.setSituacao(SituacaoEnum.LIXEIRA);  // Altera a situação para LIXEIRA
            return null;
        }).when(entityService).changeStatusToOther(entity, SituacaoEnum.LIXEIRA);

        // Act
        crudPai.remover(id);

        // Assert
        // Verifica se o status da entidade foi alterado para LIXEIRA
        assertEquals(SituacaoEnum.LIXEIRA, entity.getSituacao());

        // Verifica se o método changeStatusToOther foi chamado com os parâmetros Pairetos
        verify(entityService, times(1)).changeStatusToOther(entity, SituacaoEnum.LIXEIRA);
    }
}

