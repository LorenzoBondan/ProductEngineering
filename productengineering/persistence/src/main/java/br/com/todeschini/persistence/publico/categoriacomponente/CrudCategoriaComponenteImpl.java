package br.com.todeschini.persistence.publico.categoriacomponente;

import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.PagedBuilder;
import br.com.todeschini.domain.business.publico.categoriacomponente.DCategoriaComponente;
import br.com.todeschini.domain.business.publico.categoriacomponente.spi.CrudCategoriaComponente;
import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.business.publico.history.api.HistoryService;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.SituacaoEnum;
import br.com.todeschini.persistence.entities.publico.Acessorio;
import br.com.todeschini.persistence.entities.publico.CategoriaComponente;
import br.com.todeschini.persistence.filters.SituacaoFilter;
import br.com.todeschini.persistence.util.AttributeMappings;
import br.com.todeschini.persistence.util.EntityService;
import br.com.todeschini.persistence.util.PageRequestUtils;
import br.com.todeschini.persistence.util.SpecificationHelper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CrudCategoriaComponenteImpl implements CrudCategoriaComponente {

    private final CategoriaComponenteRepository repository;
    private final CategoriaComponenteQueryRepository queryRepository;
    private final CategoriaComponenteDomainToEntityAdapter adapter;
    private final EntityService entityService;
    private final PageRequestUtils pageRequestUtils;
    private final HistoryService historyService;
    private final SituacaoFilter<CategoriaComponente> situacaoFilter;

    public CrudCategoriaComponenteImpl(CategoriaComponenteRepository repository, CategoriaComponenteQueryRepository queryRepository, CategoriaComponenteDomainToEntityAdapter adapter, EntityService entityService,
                                       PageRequestUtils pageRequestUtils, HistoryService historyService, SituacaoFilter<CategoriaComponente> situacaoFilter) {
        this.repository = repository;
        this.queryRepository = queryRepository;
        this.adapter = adapter;
        this.entityService = entityService;
        this.pageRequestUtils = pageRequestUtils;
        this.historyService = historyService;
        this.situacaoFilter = situacaoFilter;
    }

    @Override
    @Transactional(readOnly = true)
    public Paged<DCategoriaComponente> buscarTodos(PageableRequest request) {
        SpecificationHelper<CategoriaComponente> helper = new SpecificationHelper<>();
        Specification<CategoriaComponente> specification = helper.buildSpecification(request.getColunas(), request.getOperacoes(), request.getValores());
        specification = situacaoFilter.addExcludeSituacaoLixeira(specification);

        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DCategoriaComponente>()
                        .withContent(r.getContent().stream().map(adapter::toDomain).toList())
                        .withSortedBy(String.join(";", request.getSort()))
                        .withFirst(r.isFirst())
                        .withLast(r.isLast())
                        .withPage(r.getNumber())
                        .withSize(r.getSize())
                        .withTotalPages(r.getTotalPages())
                        .withNumberOfElements(Math.toIntExact(r.getTotalElements()))
                        .build())
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DCategoriaComponente> pesquisarPorDescricao(String descricao) {
        return queryRepository.findByDescricaoIgnoreCase(descricao).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DCategoriaComponente buscar(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DHistory<DCategoriaComponente>> buscarHistorico(Integer id) {
        return historyService.getHistoryEntityByRecord(CategoriaComponente.class, "tb_categoria_componente", id.toString(), AttributeMappings.CATEGORIACOMPONENTE.getMappings()).stream()
                .map(history -> new DHistory<>(history.getId(), history.getDate(), history.getAuthor(), adapter.toDomain(history.getEntity())))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> buscarAtributosEditaveisEmLote() {
        return List.of();
    }

    @Override
    @Transactional
    public DCategoriaComponente inserir(DCategoriaComponente obj) {
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DCategoriaComponente atualizar(DCategoriaComponente obj) {
        if(!repository.existsById(obj.getCodigo())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getCodigo());
        }
        CategoriaComponente entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        setCreationProperties(entity);
        return adapter.toDomain(repository.save(entity));
    }

    @Override
    @Transactional
    public DCategoriaComponente substituirPorVersaoAntiga(Integer id, Integer versionId) {
        DHistory<CategoriaComponente> antiga = historyService.getHistoryEntityByRecord(CategoriaComponente.class, "tb_categoria_componente", id.toString(), AttributeMappings.CATEGORIACOMPONENTE.getMappings())
                .stream()
                .filter(historyWithId -> historyWithId.getId().equals(versionId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Versão " + versionId + " não encontrada para o código " + id));
        return adapter.toDomain(repository.save(antiga.getEntity()));
    }

    @Override
    public List<DCategoriaComponente> atualizarEmLote(List<DCategoriaComponente> obj) {
        return List.of();
    }

    @Override
    @Transactional
    public void inativar(Integer id) {
        CategoriaComponente entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
        SituacaoEnum situacao = entity.getSituacao() == SituacaoEnum.ATIVO ? SituacaoEnum.INATIVO : SituacaoEnum.ATIVO;
        entity.setSituacao(situacao);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void remover(Integer id) {
        entityService.changeStatusToOther(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)), SituacaoEnum.LIXEIRA);
    }

    private void setCreationProperties(CategoriaComponente obj){
        obj.setCriadoem(repository.findCriadoemById(obj.getCdcategoriaComponente()));
        obj.setCriadopor(repository.findCriadoporById(obj.getCdcategoriaComponente()));
    }
}
