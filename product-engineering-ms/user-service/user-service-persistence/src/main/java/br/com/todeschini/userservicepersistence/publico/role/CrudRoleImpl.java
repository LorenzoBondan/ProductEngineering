package br.com.todeschini.userservicepersistence.publico.role;

import br.com.todeschini.libexceptionhandler.exceptions.ResourceNotFoundException;
import br.com.todeschini.libspecificationhandler.PageRequestUtils;
import br.com.todeschini.libspecificationhandler.SpecificationHelper;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import br.com.todeschini.libvalidationhandler.pageable.PagedBuilder;
import br.com.todeschini.userservicedomain.role.DRole;
import br.com.todeschini.userservicedomain.role.spi.CrudRole;
import br.com.todeschini.userservicepersistence.entities.Role;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CrudRoleImpl implements CrudRole {

    private final RoleRepository repository;
    private final RoleQueryRepository queryRepository;
    private final RoleDomainToEntityAdapter adapter;
    private final PageRequestUtils pageRequestUtils;

    public CrudRoleImpl(RoleRepository repository, RoleQueryRepository queryRepository, RoleDomainToEntityAdapter adapter, PageRequestUtils pageRequestUtils) {
        this.repository = repository;
        this.queryRepository = queryRepository;
        this.adapter = adapter;
        this.pageRequestUtils = pageRequestUtils;
    }

    @Override
    public Paged<DRole> findAll(PageableRequest request) {
        SpecificationHelper<Role> helper = new SpecificationHelper<>();
        Specification<Role> specification = helper.buildSpecification(request.getColumns(), request.getOperations(), request.getValues());
        
        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DRole>()
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
    public DRole find(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id)));
    }

    @Override
    public DRole insert(DRole obj) {
        return null;
    }

    @Override
    public DRole update(DRole obj) {
        return null;
    }

    @Override
    public void delete(Integer obj) {
    }
}
