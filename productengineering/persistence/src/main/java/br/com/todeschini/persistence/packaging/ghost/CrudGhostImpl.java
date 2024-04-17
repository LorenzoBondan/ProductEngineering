package br.com.todeschini.persistence.packaging.ghost;

import br.com.todeschini.domain.business.packaging.ghost.DGhost;
import br.com.todeschini.domain.business.packaging.ghost.spi.CrudGhost;
import br.com.todeschini.domain.exceptions.DatabaseException;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.packaging.Ghost;
import br.com.todeschini.persistence.util.EntityService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
public class CrudGhostImpl implements CrudGhost {

    private final GhostRepository repository;
    private final GhostDomainToEntityAdapter adapter;
    private final EntityService entityService;

    public CrudGhostImpl(GhostRepository repository, GhostDomainToEntityAdapter adapter, EntityService entityService) {
        this.repository = repository;
        this.adapter = adapter;
        this.entityService = entityService;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DGhost> findByDescription(String description) {
        return repository.findByDescription(description).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DGhost> findAllActiveAndCurrentOne(String obj) {
        return repository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DGhost find(String id) {
        if(repository.existsByCode(id)){
            return adapter.toDomain(repository.findByCode(id));
        } else{
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
    }

    @Override
    @Transactional
    public DGhost insert(DGhost obj) {
        if(repository.existsByCode(obj.getCode())){
            throw new DuplicatedResourceException("Código já existente: " + obj.getCode());
        }
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DGhost update(String id, DGhost obj) {
        if(!repository.existsByCode(id)){
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
        Ghost entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        setCreationProperties(entity);
        repository.save(entity);
        return adapter.toDomain(entity);
    }

    @Override
    @Transactional
    public void inactivate(String id) {
        if(repository.existsByCode(id)){
            Ghost entity = repository.findByCode(id);
            Status newStatus = entity.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE;
            entity.setStatus(newStatus);
            repository.save(entity);
        } else{
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(String id) {
        if (!repository.existsByCode(id)) {
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
        try{
            repository.deleteByCode(id);
        } catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade. Existem dependências relacionadas a esse objeto");
        }
    }

    private void setCreationProperties(Ghost obj){
        obj.setCreationDate(repository.findCreationDate(obj.getCode()));
        obj.setCreatedBy(repository.findCreatedBy(obj.getCode()));
    }
}
