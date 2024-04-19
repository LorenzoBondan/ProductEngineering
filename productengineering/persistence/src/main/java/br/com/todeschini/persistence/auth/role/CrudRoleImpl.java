package br.com.todeschini.persistence.auth.role;

import br.com.todeschini.domain.business.auth.role.DRole;
import br.com.todeschini.domain.business.auth.role.spi.CrudRole;
import br.com.todeschini.domain.exceptions.DatabaseException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.auth.Role;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
public class CrudRoleImpl implements CrudRole {

    private final RoleRepository repository;
    private final RoleDomainToEntityAdapter adapter;

    public CrudRoleImpl(RoleRepository repository, RoleDomainToEntityAdapter adapter) {
        this.repository = repository;
        this.adapter = adapter;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DRole> findByAuthority(String authority) {
        return repository.findByAuthority(authority).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DRole find(Long id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DRole insert(DRole obj) {
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DRole update(Long id, DRole obj) {
        if(!repository.existsById(obj.getId())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getId());
        }
        Role entity = adapter.toEntity(obj);
        repository.save(entity);
        return adapter.toDomain(entity);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
        try{
            repository.deleteById(id);
        } catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade. Existem dependências relacionadas a esse objeto");
        }
    }

    @Override
    public void inactivate(Long obj) {
    }

    @Override
    public List<DRole> findAllActiveAndCurrentOne(Long obj) {
        return null;
    }
}
