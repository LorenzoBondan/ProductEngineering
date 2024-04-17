package br.com.todeschini.persistence.aluminium.usedscrew;

import br.com.todeschini.domain.business.aluminium.usedscrew.DUsedScrew;
import br.com.todeschini.domain.business.aluminium.usedscrew.spi.CrudUsedScrew;
import br.com.todeschini.domain.exceptions.DatabaseException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.aluminium.UsedScrew;
import br.com.todeschini.persistence.entities.enums.Status;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
public class CrudUsedScrewImpl implements CrudUsedScrew {

    private final UsedScrewRepository repository;
    private final UsedScrewDomainToEntityAdapter adapter;

    public CrudUsedScrewImpl(UsedScrewRepository repository, UsedScrewDomainToEntityAdapter adapter) {
        this.repository = repository;
        this.adapter = adapter;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DUsedScrew> findByScrewAndAluminiumSon(Long ScrewId, Long aluminiumSonId) {
        return repository.findByScrewIdAndAluminiumSonId(ScrewId, aluminiumSonId).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DUsedScrew> findAllActiveAndCurrentOne(Long obj) {
        return repository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DUsedScrew find(Long id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DUsedScrew insert(DUsedScrew obj) {
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DUsedScrew update(Long id, DUsedScrew obj) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
        UsedScrew entity = adapter.toEntity(obj);
        setCreationProperties(entity);
        repository.save(entity);
        return adapter.toDomain(entity);
    }

    @Override
    @Transactional
    public void inactivate(Long id) {
        UsedScrew entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
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

    private void setCreationProperties(UsedScrew obj){
        obj.setCreationDate(repository.findCreationDate(obj.getId()));
        obj.setCreatedBy(repository.findCreatedBy(obj.getId()));
    }
}
