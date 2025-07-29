package br.com.todeschini.itemservicepersistence.publico.medidas;

import br.com.todeschini.itemservicedomain.medidas.DMedidas;
import br.com.todeschini.itemservicedomain.medidas.spi.CrudMedidas;
import br.com.todeschini.itemservicepersistence.entities.Medidas;
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
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CrudMedidasImpl implements CrudMedidas {

    private final MedidasRepository repository;
    private final MedidasQueryRepository queryRepository;
    private final MedidasDomainToEntityAdapter adapter;
    private final EntityService entityService;
    private final PageRequestUtils pageRequestUtils;
    private final SituacaoFilter<Medidas> situacaoFilter;
    private final AuditoriaService auditoriaService;

    @Override
    public Paged<DMedidas> buscar(PageableRequest request) {
        SpecificationHelper<Medidas> helper = new SpecificationHelper<>();
        Specification<Medidas> specification = helper.buildSpecification(request.getColumns(), request.getOperations(), request.getValues());
        specification = situacaoFilter.addExcludeSituacaoLixeira(specification);

        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DMedidas>()
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
    public List<DMedidas> pesquisarPorAlturaELarguraEEspessura(Integer altura, Integer largura, Integer espessura) {
        return queryRepository.findByAlturaAndLarguraAndEspessura(altura, largura, espessura).stream().map(adapter::toDomain).toList();
    }

    @Override
    public DMedidas buscar(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    public DMedidas incluir(DMedidas obj) {
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    public DMedidas atualizar(DMedidas obj) {
        if(!repository.existsById(obj.getCodigo())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getCodigo());
        }
        Medidas entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        auditoriaService.setCreationProperties(entity);
        return adapter.toDomain(repository.save(entity));
    }

    @Override
    public void inativar(Integer id) {
        Medidas entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
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
