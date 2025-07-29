package br.com.todeschini.userservicepersistence.publico.user;

import br.com.todeschini.libauditpersistence.entities.enums.SituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.DatabaseException;
import br.com.todeschini.libexceptionhandler.exceptions.ResourceNotFoundException;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libspecificationhandler.PageRequestUtils;
import br.com.todeschini.libspecificationhandler.SpecificationHelper;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import br.com.todeschini.libvalidationhandler.pageable.PagedBuilder;
import br.com.todeschini.userservicedomain.user.DUser;
import br.com.todeschini.userservicedomain.user.spi.CrudUser;
import br.com.todeschini.userservicepersistence.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CrudUserImpl implements CrudUser {

    private final UserRepository repository;
    private final UserQueryRepository queryRepository;
    private final UserDomainToEntityAdapter adapter;
    private final PageRequestUtils pageRequestUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Paged<DUser> buscar(PageableRequest request) {
        SpecificationHelper<User> helper = new SpecificationHelper<>();
        Specification<User> specification = helper.buildSpecification(request.getColumns(), request.getOperations(), request.getValues());

        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DUser>()
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
    public List<DUser> findByEmail(String email) {
        return repository.findByEmail(email).stream().map(adapter::toDomain).toList();
    }

    @Override
    public DUser buscar(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id)));
    }

    @Override
    public DUser incluir(DUser obj) {
        obj.setPassword(passwordEncoder.encode(obj.getPassword()));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    public DUser atualizar(DUser obj) {
        if(!repository.existsById(obj.getId())){
            throw new ResourceNotFoundException("Id not found: " + obj.getId());
        }
        if (!obj.getPassword().startsWith("$2a$")) {  // BCrypt hashes start with $2a$
            obj.setPassword(passwordEncoder.encode(obj.getPassword()));
        }
        User entity = adapter.toEntity(obj);
        return adapter.toDomain(repository.save(entity));
    }

    @Override
    public void inativar(Integer id) {
        User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
        if (entity.getSituacao() == SituacaoEnum.LIXEIRA) {
            throw new ValidationException("Não é possível ativar/inativar um registro excluído.");
        }
        SituacaoEnum situacao = entity.getSituacao() == SituacaoEnum.ATIVO ? SituacaoEnum.INATIVO : SituacaoEnum.ATIVO;
        entity.setSituacao(situacao);
        repository.save(entity);
    }

    @Override
    public void updatePassword(String newPassword, String oldPassword, DUser user) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ValidationException("Incorrect old password");
        }
        repository.updatePassword(newPassword, user.getId());
    }

    @Override
    public void excluir(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
        try{
            repository.deleteById(id);
        } catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
