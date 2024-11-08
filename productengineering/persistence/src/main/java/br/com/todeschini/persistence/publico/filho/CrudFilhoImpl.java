package br.com.todeschini.persistence.publico.filho;

import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.PagedBuilder;
import br.com.todeschini.domain.business.publico.filho.DFilho;
import br.com.todeschini.domain.business.publico.filho.spi.CrudFilho;
import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.business.publico.history.api.HistoryService;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.SituacaoEnum;
import br.com.todeschini.persistence.entities.publico.Filho;
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
public class CrudFilhoImpl implements CrudFilho {

    private final FilhoRepository repository;
    private final FilhoQueryRepository queryRepository;
    private final FilhoDomainToEntityAdapter adapter;
    private final EntityService entityService;
    private final PageRequestUtils pageRequestUtils;
    private final HistoryService historyService;

    public CrudFilhoImpl(FilhoRepository repository, FilhoQueryRepository queryRepository, FilhoDomainToEntityAdapter adapter, EntityService entityService,
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
    public Paged<DFilho> buscarTodos(PageableRequest request) {
        SpecificationHelper<Filho> helper = new SpecificationHelper<>();
        Specification<Filho> specification = helper.buildSpecification(request.getColunas(), request.getOperacoes(), request.getValores());
        
        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DFilho>()
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
    public Collection<? extends DFilho> pesquisarPorDescricaoECorEMedidas(String descricao, Integer cdcor, Integer cdmedidas) {
        return queryRepository.findByDescricaoIgnoreCaseAndCor_CdcorAndMedidas_Cdmedidas(descricao, cdcor, cdmedidas).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DFilho> pesquisarPorDescricaoEMedidas(String descricao, Integer cdmedidas) {
        return queryRepository.findByDescricaoIgnoreCaseAndMedidas_Cdmedidas(descricao, cdmedidas).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DFilho> buscarTodosAtivosMaisAtual(Integer obj) {
        return queryRepository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DFilho buscar(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DHistory<DFilho>> buscarHistorico(Integer id) {
        return historyService.getHistoryEntityByRecord(Filho.class, "tb_filho", id.toString(), AttributeMappings.FILHO.getMappings()).stream()
                .map(history -> new DHistory<>(history.getId(), history.getDate(), history.getAuthor(), adapter.toDomain(history.getEntity())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> buscarAtributosEditaveisEmLote() {
        return entityService.obterAtributosEditaveis(DFilho.class);
    }

    @Override
    @Transactional
    public DFilho inserir(DFilho obj) {
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DFilho atualizar(DFilho obj) {
        if(!repository.existsById(obj.getCodigo())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getCodigo());
        }
        Filho entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        setCreationProperties(entity);
        return adapter.toDomain(repository.save(entity));
    }

    @Override
    @Transactional
    public List<DFilho> atualizarEmLote(List<DFilho> list) {
        return list;
    }

    @Override
    @Transactional
    public DFilho substituirPorVersaoAntiga(Integer id, Integer versionId) {
        DHistory<Filho> antiga = historyService.getHistoryEntityByRecord(Filho.class, "tb_filho", id.toString(), AttributeMappings.FILHO.getMappings())
                .stream()
                .filter(historyWithId -> historyWithId.getId().equals(versionId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Versão " + versionId + " não encontrada para o código " + id));
        return adapter.toDomain(repository.save(antiga.getEntity()));
    }

    @Override
    @Transactional
    public void inativar(Integer id) {
        Filho entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
        SituacaoEnum situacao = entity.getSituacao() == SituacaoEnum.ATIVO ? SituacaoEnum.INATIVO : SituacaoEnum.ATIVO;
        entity.setSituacao(situacao);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void remover(Integer id) {
        entityService.changeStatusToOther(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)), SituacaoEnum.LIXEIRA);
    }

    private void setCreationProperties(Filho obj){
        obj.setCriadoem(repository.findCriadoemById(obj.getCdfilho()));
        obj.setCriadopor(repository.findCriadoporById(obj.getCdfilho()));
    }
}
