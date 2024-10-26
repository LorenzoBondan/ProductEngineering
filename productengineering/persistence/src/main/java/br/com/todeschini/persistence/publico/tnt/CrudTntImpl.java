package br.com.todeschini.persistence.publico.tnt;

import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.PagedBuilder;
import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.business.publico.history.api.HistoryService;
import br.com.todeschini.domain.business.publico.tnt.DTnt;
import br.com.todeschini.domain.business.publico.tnt.spi.CrudTnt;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.Situacao;
import br.com.todeschini.persistence.entities.publico.Tnt;
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
public class CrudTntImpl implements CrudTnt {

    private final TntRepository repository;
    private final TntQueryRepository queryRepository;
    private final TntDomainToEntityAdapter adapter;
    private final EntityService entityService;
    private final PageRequestUtils pageRequestUtils;
    private final HistoryService historyService;

    public CrudTntImpl(TntRepository repository, TntQueryRepository queryRepository, TntDomainToEntityAdapter adapter, EntityService entityService,
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
    public Paged<DTnt> buscarTodos(PageableRequest request) {
        SpecificationHelper<Tnt> helper = new SpecificationHelper<>();
        Specification<Tnt> specification = helper.buildSpecification(request.getColunas(), request.getOperacoes(), request.getValores());
        
        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DTnt>()
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
    public Collection<? extends DTnt> pesquisarPorDescricao(String descricao) {
        return queryRepository.findByDescricaoIgnoreCase(descricao).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DTnt> buscarTodosAtivosMaisAtual(Integer obj) {
        return queryRepository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DTnt buscar(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DHistory<DTnt>> buscarHistorico(Integer id) {
        return historyService.getHistoryEntityByRecord(Tnt.class, "tb_material", id.toString(), AttributeMappings.MATERIAL.getMappings()).stream()
                .map(history -> new DHistory<>(history.getId(), history.getDate(), history.getAuthor(), adapter.toDomain(history.getEntity())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> buscarAtributosEditaveisEmLote() {
        return entityService.obterAtributosEditaveis(DTnt.class);
    }

    @Override
    @Transactional
    public DTnt inserir(DTnt obj) {
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DTnt atualizar(DTnt obj) {
        if(!repository.existsById(obj.getCodigo())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getCodigo());
        }
        Tnt entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        setCreationProperties(entity);
        return adapter.toDomain(repository.save(entity));
    }

    @Override
    @Transactional
    public List<DTnt> atualizarEmLote(List<DTnt> list) {
        return list;
    }

    @Override
    @Transactional
    public DTnt substituirPorVersaoAntiga(Integer id, Integer versionId) {
        DHistory<Tnt> antiga = historyService.getHistoryEntityByRecord(Tnt.class, "tb_material", id.toString(), AttributeMappings.MATERIAL.getMappings())
                .stream()
                .filter(historyWithId -> historyWithId.getId().equals(versionId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Versão " + versionId + " não encontrada para o código " + id));
        return adapter.toDomain(repository.save(antiga.getEntity()));
    }

    @Override
    @Transactional
    public void inativar(Integer id) {
        Tnt entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
        Situacao situacao = entity.getSituacao() == Situacao.ATIVO ? Situacao.INATIVO : Situacao.ATIVO;
        entity.setSituacao(situacao);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void remover(Integer id) {
        entityService.changeStatusToOther(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)), Situacao.LIXEIRA);
    }

    private void setCreationProperties(Tnt obj){
        obj.setCriadoem(repository.findCriadoemById(obj.getCdmaterial()));
        obj.setCriadopor(repository.findCriadoporById(obj.getCdmaterial()));
    }
}
