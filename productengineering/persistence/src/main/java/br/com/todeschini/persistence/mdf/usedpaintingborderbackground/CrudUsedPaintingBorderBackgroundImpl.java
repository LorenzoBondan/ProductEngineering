package br.com.todeschini.persistence.mdf.usedpaintingborderbackground;

import br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.DUsedPaintingBorderBackground;
import br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.spi.CrudUsedPaintingBorderBackground;
import br.com.todeschini.domain.exceptions.DatabaseException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.mdf.UsedPaintingBorderBackground;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
public class CrudUsedPaintingBorderBackgroundImpl implements CrudUsedPaintingBorderBackground {

    private final UsedPaintingBorderBackgroundRepository repository;
    private final UsedPaintingBorderBackgroundDomainToEntityAdapter adapter;

    public CrudUsedPaintingBorderBackgroundImpl(UsedPaintingBorderBackgroundRepository repository, UsedPaintingBorderBackgroundDomainToEntityAdapter adapter) {
        this.repository = repository;
        this.adapter = adapter;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DUsedPaintingBorderBackground> findByPaintingBorderBackgroundAndPaintingSon(Long PaintingBorderBackgroundId, Long sonId) {
        return repository.findByPaintingBorderBackgroundIdAndMDPSonId(PaintingBorderBackgroundId, sonId).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DUsedPaintingBorderBackground> findAllActiveAndCurrentOne(Long obj) {
        return repository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DUsedPaintingBorderBackground find(Long id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DUsedPaintingBorderBackground insert(DUsedPaintingBorderBackground obj) {
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DUsedPaintingBorderBackground update(Long id, DUsedPaintingBorderBackground obj) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
        UsedPaintingBorderBackground entity = adapter.toEntity(obj);
        setCreationProperties(entity);
        repository.save(entity);
        return adapter.toDomain(entity);
    }

    @Override
    @Transactional
    public void inactivate(Long id) {
        UsedPaintingBorderBackground entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
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

    private void setCreationProperties(UsedPaintingBorderBackground obj){
        obj.setCreationDate(repository.findCreationDate(obj.getId()));
        obj.setCreatedBy(repository.findCreatedBy(obj.getId()));
    }
}
