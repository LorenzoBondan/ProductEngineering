package br.com.todeschini.mdpservicepersistence.publico.cola;

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
import br.com.todeschini.mdpservicedomain.cola.DCola;
import br.com.todeschini.mdpservicedomain.cola.spi.CrudCola;
import br.com.todeschini.mdpservicepersistence.entities.Cola;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CrudColaImpl implements CrudCola {

    private final ColaRepository repository;
    private final ColaQueryRepository queryRepository;
    private final ColaDomainToEntityAdapter adapter;
    private final EntityService entityService;
    private final PageRequestUtils pageRequestUtils;
    private final SituacaoFilter<Cola> situacaoFilter;
    private final AuditoriaService auditoriaService;

    @Override
    public Paged<DCola> buscar(PageableRequest request) {
        SpecificationHelper<Cola> helper = new SpecificationHelper<>();
        Specification<Cola> specification = helper.buildSpecification(request.getColumns(), request.getOperations(), request.getValues());
        specification = situacaoFilter.addExcludeSituacaoLixeira(specification);

        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DCola>()
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
    public List<DCola> pesquisarPorDescricao(String descricao) {
        return queryRepository.findByDescricaoIgnoreCase(descricao).stream().map(adapter::toDomain).toList();
    }

    @Override
    public DCola buscar(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    public DCola incluir(DCola obj) {
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    public DCola atualizar(DCola obj) {
        if(!repository.existsById(obj.getCodigo())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getCodigo());
        }
        Cola entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        auditoriaService.setCreationProperties(entity);
        return adapter.toDomain(repository.save(entity));
    }

    @Override
    public void inativar(Integer id) {
        Cola entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
        if (entity.getSituacao() == SituacaoEnum.LIXEIRA) {
            throw new ValidationException("Não é possível ativar/inativar um registro excluído.");
        }
        SituacaoEnum situacao = entity.getSituacao() == SituacaoEnum.ATIVO ? SituacaoEnum.INATIVO : SituacaoEnum.ATIVO;
        entity.setSituacao(situacao);
        repository.save(entity);
    }

    @Override
    public void excluir(Integer id) {
        entityService.changeStatusToOther(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)), DSituacaoEnum.LIXEIRA);
    }
}
