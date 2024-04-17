package br.com.todeschini.persistence.aluminium.screw;

import br.com.todeschini.domain.business.aluminium.screw.DScrew;
import br.com.todeschini.domain.business.aluminium.screw.spi.CrudScrew;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.aluminium.Screw;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.EntityService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
public class CrudScrewImpl implements CrudScrew {

    private final ScrewRepository repository;
    private final ItemRepository itemRepository;
    private final ScrewDomainToEntityAdapter adapter;
    private final EntityService entityService;

    public CrudScrewImpl(ScrewRepository repository, ItemRepository itemRepository, ScrewDomainToEntityAdapter adapter, EntityService entityService) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.adapter = adapter;
        this.entityService = entityService;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DScrew> findByDescription(String description) {
        return repository.findByDescription(description).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DScrew> findAllActiveAndCurrentOne(Long obj) {
        return repository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DScrew find(Long id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DScrew insert(DScrew obj) {
        if(itemRepository.existsById(obj.getCode())){
            throw new DuplicatedResourceException("Código já existente: " + obj.getCode());
        }
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DScrew update(Long id, DScrew obj) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
        Screw entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        setCreationProperties(entity);
        repository.save(entity);
        return adapter.toDomain(entity);
    }

    @Override
    @Transactional
    public void inactivate(Long id) {
        Screw entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
        Status newStatus = entity.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE;
        entity.setStatus(newStatus);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entityService.changeStatusToOther(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)), Status.DELETED);
    }

    private void setCreationProperties(Screw obj){
        obj.setCreationDate(itemRepository.findCreationDate(obj.getCode()));
        obj.setCreatedBy(itemRepository.findCreatedBy(obj.getCode()));
    }
}
