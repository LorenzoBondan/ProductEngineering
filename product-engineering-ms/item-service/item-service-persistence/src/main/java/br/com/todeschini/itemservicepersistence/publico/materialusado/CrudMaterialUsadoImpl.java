package br.com.todeschini.itemservicepersistence.publico.materialusado;

import br.com.todeschini.itemservicedomain.filho.api.FilhoService;
import br.com.todeschini.itemservicedomain.material.api.MaterialService;
import br.com.todeschini.itemservicedomain.materialusado.DMaterialUsado;
import br.com.todeschini.itemservicedomain.materialusado.spi.CrudMaterialUsado;
import br.com.todeschini.itemservicepersistence.entities.MaterialUsado;
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
public class CrudMaterialUsadoImpl implements CrudMaterialUsado {

    private final MaterialUsadoRepository repository;
    private final MaterialUsadoQueryRepository queryRepository;
    private final MaterialUsadoDomainToEntityAdapter adapter;
    private final EntityService entityService;
    private final PageRequestUtils pageRequestUtils;
    private final SituacaoFilter<MaterialUsado> situacaoFilter;
    private final FilhoService filhoService;
    private final MaterialService materialService;
    private final AuditoriaService auditoriaService;

    @Override
    public Paged<DMaterialUsado> buscar(PageableRequest request) {
        SpecificationHelper<MaterialUsado> helper = new SpecificationHelper<>();
        Specification<MaterialUsado> specification = helper.buildSpecification(request.getColumns(), request.getOperations(), request.getValues());
        specification = situacaoFilter.addExcludeSituacaoLixeira(specification);

        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DMaterialUsado>()
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
    public List<DMaterialUsado> pesquisarPorFilhoEMaterial(Integer cdfilho, Integer cdmaterial) {
        return queryRepository.findByFilho_CdfilhoAndMaterial_Cdmaterial(cdfilho, cdmaterial).stream().map(adapter::toDomain).toList();
    }

    @Override
    public DMaterialUsado buscar(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    public DMaterialUsado incluir(DMaterialUsado obj) {
        obj.setFilho(filhoService.buscar(obj.getFilho().getCodigo()));
        obj.setMaterial(materialService.buscar(obj.getMaterial().getCodigo()));
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    public DMaterialUsado atualizar(DMaterialUsado obj) {
        if(!repository.existsById(obj.getCodigo())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getCodigo());
        }
        obj.setFilho(filhoService.buscar(obj.getFilho().getCodigo()));
        obj.setMaterial(materialService.buscar(obj.getMaterial().getCodigo()));
        MaterialUsado entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        auditoriaService.setCreationProperties(entity);
        return adapter.toDomain(repository.save(entity));
    }

    @Override
    public void inativar(Integer id) {
        MaterialUsado entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
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
