package br.com.todeschini.persistence.mdf.painting;

import br.com.todeschini.domain.business.mdf.painting.DPainting;
import br.com.todeschini.domain.business.mdf.painting.spi.CrudPainting;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.mdf.Painting;
import br.com.todeschini.persistence.util.EntityService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
public class CrudPaintingImpl implements CrudPainting {

    private final PaintingRepository repository;
    private final PaintingDomainToEntityAdapter adapter;
    private final EntityService entityService;

    public CrudPaintingImpl(PaintingRepository repository, PaintingDomainToEntityAdapter adapter, EntityService entityService) {
        this.repository = repository;
        this.adapter = adapter;
        this.entityService = entityService;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DPainting> findByDescription(String description) {
        return repository.findByDescription(description).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DPainting> findAllActiveAndCurrentOne(Long obj) {
        return repository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DPainting find(Long id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DPainting insert(DPainting obj) {
        if(repository.existsById(obj.getCode())){
            throw new DuplicatedResourceException("Código já existente: " + obj.getCode());
        }
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DPainting update(Long id, DPainting obj) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
        Painting entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        setCreationProperties(entity);
        repository.save(entity);
        return adapter.toDomain(entity);
    }

    @Override
    @Transactional
    public void inactivate(Long id) {
        Painting entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
        Status newStatus = entity.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE;
        entity.setStatus(newStatus);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entityService.changeStatusToOther(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)), Status.DELETED);
    }

    private void setCreationProperties(Painting obj){
        obj.setCreationDate(repository.findCreationDate(obj.getCode()));
        obj.setCreatedBy(repository.findCreatedBy(obj.getCode()));
    }
}
