package br.com.todeschini.persistence.publico.polietileno;

import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.PagedBuilder;
import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.business.publico.history.api.HistoryService;
import br.com.todeschini.domain.business.publico.polietileno.DPolietileno;
import br.com.todeschini.domain.business.publico.polietileno.spi.CrudPolietileno;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.Situacao;
import br.com.todeschini.persistence.entities.publico.Polietileno;
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
public class CrudPolietilenoImpl implements CrudPolietileno {

    private final PolietilenoRepository repository;
    private final PolietilenoQueryRepository queryRepository;
    private final PolietilenoDomainToEntityAdapter adapter;
    private final EntityService entityService;
    private final PageRequestUtils pageRequestUtils;
    private final HistoryService historyService;

    public CrudPolietilenoImpl(PolietilenoRepository repository, PolietilenoQueryRepository queryRepository, PolietilenoDomainToEntityAdapter adapter, EntityService entityService,
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
    public Paged<DPolietileno> buscarTodos(PageableRequest request) {
        SpecificationHelper<Polietileno> helper = new SpecificationHelper<>();
        Specification<Polietileno> specification = helper.buildSpecification(request.getColunas(), request.getOperacoes(), request.getValores());
        
        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DPolietileno>()
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
    public Collection<? extends DPolietileno> pesquisarPorDescricao(String descricao) {
        return queryRepository.findByDescricaoIgnoreCase(descricao).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DPolietileno> buscarTodosAtivosMaisAtual(Integer obj) {
        return queryRepository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DPolietileno buscar(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DHistory<DPolietileno>> buscarHistorico(Integer id) {
        return historyService.getHistoryEntityByRecord(Polietileno.class, "tb_material", id.toString(), AttributeMappings.MATERIAL.getMappings()).stream()
                .map(history -> new DHistory<>(history.getId(), history.getDate(), history.getAuthor(), adapter.toDomain(history.getEntity())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> buscarAtributosEditaveisEmLote() {
        return entityService.obterAtributosEditaveis(DPolietileno.class);
    }

    @Override
    @Transactional
    public DPolietileno inserir(DPolietileno obj) {
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DPolietileno atualizar(DPolietileno obj) {
        if(!repository.existsById(obj.getCodigo())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getCodigo());
        }
        Polietileno entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        setCreationProperties(entity);
        return adapter.toDomain(repository.save(entity));
    }

    @Override
    @Transactional
    public List<DPolietileno> atualizarEmLote(List<DPolietileno> list) {
        return list;
    }

    @Override
    @Transactional
    public DPolietileno substituirPorVersaoAntiga(Integer id, Integer versionId) {
        DHistory<Polietileno> antiga = historyService.getHistoryEntityByRecord(Polietileno.class, "tb_material", id.toString(), AttributeMappings.MATERIAL.getMappings())
                .stream()
                .filter(historyWithId -> historyWithId.getId().equals(versionId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Versão " + versionId + " não encontrada para o código " + id));
        return adapter.toDomain(repository.save(antiga.getEntity()));
    }

    @Override
    @Transactional
    public void inativar(Integer id) {
        Polietileno entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
        Situacao situacao = entity.getSituacao() == Situacao.ATIVO ? Situacao.INATIVO : Situacao.ATIVO;
        entity.setSituacao(situacao);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void remover(Integer id) {
        entityService.changeStatusToOther(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)), Situacao.LIXEIRA);
    }

    private void setCreationProperties(Polietileno obj){
        obj.setCriadoem(repository.findCriadoemById(obj.getCdmaterial()));
        obj.setCriadopor(repository.findCriadoporById(obj.getCdmaterial()));
    }
}
