package br.com.todeschini.persistence.aluminium.aluminiumson;

import br.com.todeschini.domain.business.aluminium.aluminiumson.DAluminiumSon;
import br.com.todeschini.domain.business.aluminium.aluminiumson.spi.CrudAluminiumSon;
import br.com.todeschini.domain.exceptions.DatabaseException;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.aluminium.AluminiumSon;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.publico.Son;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.EntityService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
public class CrudAluminiumSonImpl implements CrudAluminiumSon {

    private final AluminiumSonRepository repository;
    private final ItemRepository itemRepository;
    private final AluminiumSonDomainToEntityAdapter adapter;
    private final EntityService entityService;

    public CrudAluminiumSonImpl(AluminiumSonRepository repository, ItemRepository itemRepository, AluminiumSonDomainToEntityAdapter adapter, EntityService entityService) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.adapter = adapter;
        this.entityService = entityService;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DAluminiumSon> findByDescription(String description) {
        return repository.findByDescription(description).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DAluminiumSon> findAllActiveAndCurrentOne(Long obj) {
        return repository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DAluminiumSon find(Long id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DAluminiumSon insert(DAluminiumSon obj) {
        if(itemRepository.existsById(obj.getCode())){
            throw new DuplicatedResourceException("Código já existente: " + obj.getCode());
        }
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DAluminiumSon update(Long id, DAluminiumSon obj) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
        AluminiumSon entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        setCreationProperties(entity);
        repository.save(entity);
        return adapter.toDomain(entity);
    }

    @Override
    @Transactional
    public void inactivate(Long id) {
        AluminiumSon entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
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

    private void setCreationProperties(Son obj){
        obj.setCreationDate(itemRepository.findCreationDate(obj.getCode()));
        obj.setCreatedBy(itemRepository.findCreatedBy(obj.getCode()));
    }
}
