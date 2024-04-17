package br.com.todeschini.persistence.guides.guidemachine;

import br.com.todeschini.domain.business.guides.guidemachine.DGuideMachine;
import br.com.todeschini.domain.business.guides.guidemachine.spi.CrudGuideMachine;
import br.com.todeschini.domain.exceptions.DatabaseException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.guides.GuideMachine;
import br.com.todeschini.persistence.util.EntityService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
public class CrudGuideMachineImpl implements CrudGuideMachine {

    private final GuideMachineRepository repository;
    private final GuideMachineDomainToEntityAdapter adapter;
    private final EntityService entityService;

    public CrudGuideMachineImpl(GuideMachineRepository repository, GuideMachineDomainToEntityAdapter adapter, EntityService entityService) {
        this.repository = repository;
        this.adapter = adapter;
        this.entityService = entityService;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DGuideMachine> findByGuideAndMachine(Long guideId, Long machineId) {
        return repository.findByGuideIdAndMachineId(guideId, machineId).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DGuideMachine> findAllActiveAndCurrentOne(Long obj) {
        return repository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DGuideMachine find(Long id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DGuideMachine insert(DGuideMachine obj) {
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DGuideMachine update(Long id, DGuideMachine obj) {
        if(!repository.existsById(obj.getId())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getId());
        }
        GuideMachine entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        setCreationProperties(entity);
        repository.save(entity);
        return adapter.toDomain(entity);
    }

    @Override
    @Transactional
    public void inactivate(Long id) {
        GuideMachine entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
        Status newStatus = entity.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE;
        entity.setStatus(newStatus);
        repository.save(entity);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Objeto não encontrado");
        }
        try{
            repository.deleteById(id);
        } catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Erro de integridade. Existem dependências vinculadas a esse objeto");
        }
    }

    private void setCreationProperties(GuideMachine obj){
        obj.setCreationDate(repository.findCreationDate(obj.getId()));
        obj.setCreatedBy(repository.findCreatedBy(obj.getId()));
    }
}
