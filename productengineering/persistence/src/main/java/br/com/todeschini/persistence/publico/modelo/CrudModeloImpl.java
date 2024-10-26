package br.com.todeschini.persistence.publico.modelo;

import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.PagedBuilder;
import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.business.publico.history.api.HistoryService;
import br.com.todeschini.domain.business.publico.modelo.DModelo;
import br.com.todeschini.domain.business.publico.modelo.spi.CrudModelo;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.Situacao;
import br.com.todeschini.persistence.entities.publico.Modelo;
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
public class CrudModeloImpl implements CrudModelo {

    private final ModeloRepository repository;
    private final ModeloQueryRepository queryRepository;
    private final ModeloDomainToEntityAdapter adapter;
    private final EntityService entityService;
    private final PageRequestUtils pageRequestUtils;
    private final HistoryService historyService;

    public CrudModeloImpl(ModeloRepository repository, ModeloQueryRepository queryRepository, ModeloDomainToEntityAdapter adapter, EntityService entityService,
                          PageRequestUtils pageRequestUtils, HistoryService historyService) {
        this.repository = repository;
        this.queryRepository = queryRepository;
        this.adapter = adapter;
        this.entityService = entityService;
        this.pageRequestUtils = pageRequestUtils;
        this.historyService = historyService;
    }

    @Override
    @Transactional(readOnly = true)
    public Paged<DModelo> buscarTodos(PageableRequest request) {
        SpecificationHelper<Modelo> helper = new SpecificationHelper<>();
        Specification<Modelo> specification = helper.buildSpecification(request.getColunas(), request.getOperacoes(), request.getValores());
        
        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DModelo>()
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
    public Collection<? extends DModelo> pesquisarPorDescricao(String descricao) {
        return queryRepository.findByDescricaoIgnoreCase(descricao).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DModelo> buscarTodosAtivosMaisAtual(Integer obj) {
        return queryRepository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DModelo buscar(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DHistory<DModelo>> buscarHistorico(Integer id) {
        return historyService.getHistoryEntityByRecord(Modelo.class, "tb_modelo", id.toString(), AttributeMappings.MODELO.getMappings()).stream()
                .map(history -> new DHistory<>(history.getId(), history.getDate(), history.getAuthor(), adapter.toDomain(history.getEntity())))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> buscarAtributosEditaveisEmLote() {
        return List.of();
    }

    @Override
    @Transactional
    public DModelo inserir(DModelo obj) {
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DModelo atualizar(DModelo obj) {
        if(!repository.existsById(obj.getCodigo())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getCodigo());
        }
        Modelo entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        setCreationProperties(entity);
        return adapter.toDomain(repository.save(entity));
    }

    @Override
    @Transactional
    public DModelo substituirPorVersaoAntiga(Integer id, Integer versionId) {
        DHistory<Modelo> antiga = historyService.getHistoryEntityByRecord(Modelo.class, "tb_modelo", id.toString(), AttributeMappings.MODELO.getMappings())
                .stream()
                .filter(historyWithId -> historyWithId.getId().equals(versionId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Versão " + versionId + " não encontrada para o código " + id));
        return adapter.toDomain(repository.save(antiga.getEntity()));
    }

    @Override
    public List<DModelo> atualizarEmLote(List<DModelo> obj) {
        return List.of();
    }

    @Override
    @Transactional
    public void inativar(Integer id) {
        Modelo entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
        Situacao situacao = entity.getSituacao() == Situacao.ATIVO ? Situacao.INATIVO : Situacao.ATIVO;
        entity.setSituacao(situacao);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void remover(Integer id) {
        entityService.changeStatusToOther(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)), Situacao.LIXEIRA);
    }

    private void setCreationProperties(Modelo obj){
        obj.setCriadoem(repository.findCriadoemById(obj.getCdmodelo()));
        obj.setCriadopor(repository.findCriadoporById(obj.getCdmodelo()));
    }
}
