package br.com.todeschini.persistence.mdp.mdpson;

import br.com.todeschini.domain.business.mdp.mdpson.DMDPSon;
import br.com.todeschini.domain.business.mdp.mdpson.spi.CrudMDPSon;
import br.com.todeschini.domain.exceptions.DatabaseException;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.mdp.MDPSon;
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
public class CrudMDPSonImpl implements CrudMDPSon {

    private final MDPSonRepository repository;
    private final ItemRepository itemRepository;
    private final MDPSonDomainToEntityAdapter adapter;
    private final EntityService entityService;

    public CrudMDPSonImpl(MDPSonRepository repository, ItemRepository itemRepository, MDPSonDomainToEntityAdapter adapter, EntityService entityService) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.adapter = adapter;
        this.entityService = entityService;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DMDPSon> findByDescription(String description) {
        return repository.findByDescription(description).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DMDPSon> findAllActiveAndCurrentOne(Long obj) {
        return repository.findAllActiveAndCurrentOne(obj).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DMDPSon find(Long id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DMDPSon insert(DMDPSon obj) {
        if(itemRepository.existsById(obj.getCode())){
            throw new DuplicatedResourceException("Código já existente: " + obj.getCode());
        }
        entityService.verifyDependenciesStatus(adapter.toEntity(obj));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DMDPSon update(Long id, DMDPSon obj) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Código não encontrado: " + id);
        }
        MDPSon entity = adapter.toEntity(obj);
        entityService.verifyDependenciesStatus(entity);
        setCreationProperties(entity);
        repository.save(entity);
        return adapter.toDomain(entity);
    }

    @Override
    @Transactional
    public void inactivate(Long id) {
        MDPSon entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
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
