package br.com.todeschini.persistence.mdp.usedglue;

import br.com.todeschini.domain.business.mdp.usedglue.DUsedGlue;
import br.com.todeschini.domain.business.mdp.usedglue.spi.CrudUsedGlue;
import br.com.todeschini.domain.exceptions.DatabaseException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.mdp.UsedGlue;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
public class CrudUsedGlueImpl implements CrudUsedGlue {

    private final UsedGlueRepository repository;
    private final UsedGlueDomainToEntityAdapter adapter;

    public CrudUsedGlueImpl(UsedGlueRepository repository, UsedGlueDomainToEntityAdapter adapter) {
        this.repository = repository;
        this.adapter = adapter;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DUsedGlue> findByGlueAndMDPSon(Long GlueId, Long mdpSonId) {
        return repository.findByGlueIdAndMDPSonId(GlueId, mdpSonId).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DUsedGlue> findAllActiveAndCurrentOne(Long obj) {
        return repository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DUsedGlue find(Long id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DUsedGlue insert(DUsedGlue obj) {
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DUsedGlue update(Long id, DUsedGlue obj) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
        UsedGlue entity = adapter.toEntity(obj);
        setCreationProperties(entity);
        repository.save(entity);
        return adapter.toDomain(entity);
    }

    @Override
    @Transactional
    public void inactivate(Long id) {
        UsedGlue entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
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

    private void setCreationProperties(UsedGlue obj){
        obj.setCreationDate(repository.findCreationDate(obj.getId()));
        obj.setCreatedBy(repository.findCreatedBy(obj.getId()));
    }
}
