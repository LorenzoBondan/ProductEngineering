package br.com.todeschini.persistence.aluminium.trysquare;

import br.com.todeschini.domain.business.aluminium.trysquare.DTrySquare;
import br.com.todeschini.domain.business.aluminium.trysquare.spi.CrudTrySquare;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.aluminium.TrySquare;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.EntityService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
public class CrudTrySquareImpl implements CrudTrySquare {

    private final TrySquareRepository repository;
    private final ItemRepository itemRepository;
    private final TrySquareDomainToEntityAdapter adapter;
    private final EntityService entityService;

    public CrudTrySquareImpl(TrySquareRepository repository, ItemRepository itemRepository, TrySquareDomainToEntityAdapter adapter, EntityService entityService) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.adapter = adapter;
        this.entityService = entityService;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DTrySquare> findByDescription(String description) {
        return repository.findByDescription(description).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DTrySquare> findAllActiveAndCurrentOne(Long obj) {
        return repository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DTrySquare find(Long id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DTrySquare insert(DTrySquare obj) {
        if(itemRepository.existsById(obj.getCode())){
            throw new DuplicatedResourceException("Código já existente: " + obj.getCode());
        }
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DTrySquare update(Long id, DTrySquare obj) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
        TrySquare entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        setCreationProperties(entity);
        repository.save(entity);
        return adapter.toDomain(entity);
    }

    @Override
    @Transactional
    public void inactivate(Long id) {
        TrySquare entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
        Status newStatus = entity.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE;
        entity.setStatus(newStatus);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entityService.changeStatusToOther(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)), Status.DELETED);
    }

    private void setCreationProperties(TrySquare obj){
        obj.setCreationDate(itemRepository.findCreationDate(obj.getCode()));
        obj.setCreatedBy(itemRepository.findCreatedBy(obj.getCode()));
    }
}
