package br.com.todeschini.itemservicepersistence.publico.cor;

import br.com.todeschini.itemservicedomain.cor.DCor;
import br.com.todeschini.itemservicedomain.cor.spi.CrudCor;
import br.com.todeschini.itemservicepersistence.entities.Cor;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libauditpersistence.entities.enums.SituacaoEnum;
import br.com.todeschini.libauditpersistence.services.AuditoriaService;
import br.com.todeschini.libexceptionhandler.exceptions.ResourceNotFoundException;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libspecificationhandler.PageRequestUtils;
import br.com.todeschini.libspecificationhandler.SpecificationHelper;
import br.com.todeschini.libspecificationhandler.filters.SituacaoFilter;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import br.com.todeschini.libvalidationhandler.pageable.PagedBuilder;
import br.com.todeschini.lixeiraservicepersistence.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CrudCorImpl implements CrudCor {

    private final CorRepository repository;
    private final CorQueryRepository queryRepository;
    private final CorDomainToEntityAdapter adapter;
    private final EntityService entityService;
    private final PageRequestUtils pageRequestUtils;
    private final SituacaoFilter<Cor> situacaoFilter;
    private final AuditoriaService auditoriaService;

    @Override
    public Paged<DCor> buscar(PageableRequest request) {
        SpecificationHelper<Cor> helper = new SpecificationHelper<>();
        Specification<Cor> specification = helper.buildSpecification(request.getColumns(), request.getOperations(), request.getValues());
        specification = situacaoFilter.addExcludeSituacaoLixeira(specification);

        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DCor>()
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
    public List<DCor> pesquisarPorDescricao(String descricao) {
        return queryRepository.findByDescricaoIgnoreCase(descricao).stream().map(adapter::toDomain).toList();
    }

    @Override
    public DCor buscar(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    public DCor incluir(DCor obj) {
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    public DCor atualizar(DCor obj) {
        if(!repository.existsById(obj.getCodigo())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getCodigo());
        }
        Cor entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        auditoriaService.setCreationProperties(entity);
        return adapter.toDomain(repository.save(entity));
    }

    @Override
    public void inativar(Integer id) {
        Cor entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
        if (Objects.equals(entity.getSituacao().getValue(), SituacaoEnum.LIXEIRA.getValue())) {
            throw new ValidationException("Não é possível ativar/inativar um registro excluído.");
        }
        SituacaoEnum situacao = Objects.equals(entity.getSituacao().getValue(), SituacaoEnum.ATIVO.getValue()) ? SituacaoEnum.INATIVO : SituacaoEnum.ATIVO;
        entity.setSituacao(situacao);
        repository.save(entity);
    }

    @Override
    public void excluir(Integer id) {
        entityService.changeStatusToOther(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)), DSituacaoEnum.LIXEIRA);
    }
}
