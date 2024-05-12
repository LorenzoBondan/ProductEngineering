package br.com.todeschini.persistence.publico.father;

import br.com.todeschini.domain.business.publico.father.DFather;
import br.com.todeschini.domain.business.publico.father.spi.CrudFather;
import br.com.todeschini.domain.exceptions.DatabaseException;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
public class CrudFatherImpl implements CrudFather {

    private final FatherRepository repository;
    private final ItemRepository itemRepository;
    private final FatherDomainToEntityAdapter adapter;

    public CrudFatherImpl(FatherRepository repository, ItemRepository itemRepository, FatherDomainToEntityAdapter adapter) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.adapter = adapter;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DFather> findByDescription(String description) {
        return repository.findByDescription(description).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DFather> findAllActiveAndCurrentOne(Long obj) {
        return repository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DFather find(Long id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DFather insert(DFather obj) {
        if(itemRepository.existsById(obj.getCode())){
            throw new DuplicatedResourceException("Código já existente: " + obj.getCode());
        }
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DFather update(Long id, DFather obj) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
        Father entity = adapter.toEntity(obj);
        setCreationProperties(entity);
        entity.calculateValue();
        repository.save(entity);
        return adapter.toDomain(entity);
    }

    @Override
    @Transactional
    public void inactivate(Long id) {
        Father entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
        Status newStatus = entity.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE;
        entity.setStatus(newStatus);
        repository.save(entity);
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

    private void setCreationProperties(Father obj){
        obj.setCreationDate(itemRepository.findCreationDate(obj.getCode()));
        obj.setCreatedBy(itemRepository.findCreatedBy(obj.getCode()));
    }
}
