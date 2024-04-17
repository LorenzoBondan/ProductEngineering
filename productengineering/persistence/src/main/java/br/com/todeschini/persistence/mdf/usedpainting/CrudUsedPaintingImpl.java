package br.com.todeschini.persistence.mdf.usedpainting;

import br.com.todeschini.domain.business.mdf.usedpainting.DUsedPainting;
import br.com.todeschini.domain.business.mdf.usedpainting.spi.CrudUsedPainting;
import br.com.todeschini.domain.exceptions.DatabaseException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.mdf.UsedPainting;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
public class CrudUsedPaintingImpl implements CrudUsedPainting {

    private final UsedPaintingRepository repository;
    private final UsedPaintingDomainToEntityAdapter adapter;

    public CrudUsedPaintingImpl(UsedPaintingRepository repository, UsedPaintingDomainToEntityAdapter adapter) {
        this.repository = repository;
        this.adapter = adapter;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DUsedPainting> findByPaintingAndPaintingSon(Long paintingId, Long sonId) {
        return repository.findByPaintingIdAndMDPSonId(paintingId, sonId).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DUsedPainting> findAllActiveAndCurrentOne(Long obj) {
        return repository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DUsedPainting find(Long id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DUsedPainting insert(DUsedPainting obj) {
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DUsedPainting update(Long id, DUsedPainting obj) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
        UsedPainting entity = adapter.toEntity(obj);
        setCreationProperties(entity);
        repository.save(entity);
        return adapter.toDomain(entity);
    }

    @Override
    @Transactional
    public void inactivate(Long id) {
        UsedPainting entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
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

    private void setCreationProperties(UsedPainting obj){
        obj.setCreationDate(repository.findCreationDate(obj.getId()));
        obj.setCreatedBy(repository.findCreatedBy(obj.getId()));
    }
}
