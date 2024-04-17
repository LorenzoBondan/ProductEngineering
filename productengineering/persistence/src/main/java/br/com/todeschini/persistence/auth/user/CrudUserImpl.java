package br.com.todeschini.persistence.auth.user;

import br.com.todeschini.domain.business.auth.user.DUser;
import br.com.todeschini.domain.business.auth.user.spi.CrudUser;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.domain.exceptions.ValidationException;
import br.com.todeschini.persistence.entities.auth.User;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.util.CustomUserUtil;
import br.com.todeschini.persistence.util.EntityService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
public class CrudUserImpl implements CrudUser {

    private final UserRepository repository;
    private final UserDomainToEntityAdapter adapter;
    private final EntityService entityService;
    private final CustomUserUtil customUserUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public CrudUserImpl(UserRepository repository, UserDomainToEntityAdapter adapter, EntityService entityService, CustomUserUtil customUserUtil, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.adapter = adapter;
        this.entityService = entityService;
        this.customUserUtil = customUserUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DUser> findByEmail(String email) {
        return repository.findByEmail(email).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DUser> findAllActiveAndCurrentOne(Long obj) {
        return repository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DUser find(Long id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DUser insert(DUser obj) {
        obj.setPassword(passwordEncoder.encode(obj.getPassword()));
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DUser update(Long id, DUser obj) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
        obj.setPassword(passwordEncoder.encode(obj.getPassword()));
        User entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        repository.save(entity);
        return adapter.toDomain(entity);
    }

    @Override
    @Transactional
    public void updatePassword(String newPassword, String oldPassword) {
        User me = repository.findByEmail(customUserUtil.getLoggedUsername()).iterator().next();
        if (!passwordEncoder.matches(oldPassword, me.getPassword())) {
            throw new ValidationException("Senha antiga incorreta");
        }
        repository.updatePassword(passwordEncoder.encode(newPassword), me.getId());
    }

    @Override
    @Transactional
    public void inactivate(Long obj) {
        User entity = repository.findById(obj).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + obj));
        Status newStatus = entity.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE;
        entity.setStatus(newStatus);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entityService.changeStatusToOther(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)), Status.DELETED);
    }
}
