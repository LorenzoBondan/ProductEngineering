package br.com.todeschini.persistence.util;

import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.domain.exceptions.ValidationException;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.trash.Trash;
import br.com.todeschini.persistence.trash.TrashRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class EntityService {

    @Autowired
    private CustomUserUtil customUserUtil;

    @Autowired
    private DynamicRepositoryFactory dynamicRepositoryFactory;

    @Autowired
    private TrashRepository trashRepository;

    @Transactional
    @SneakyThrows
    public <T> void changeStatusToAtivo(T entity, Status status, Boolean retrieveDependencies) {
        updateStatusAtivoRecursively(entity, status, new HashSet<>(), retrieveDependencies);
        saveEntity(entity);
    }

    @Transactional
    @SneakyThrows
    public <T> void changeStatusToOther(T entity, Status status) {
        updateStatusOtherRecursively(entity, status, new HashSet<>());
        saveEntity(entity);
    }

    @SneakyThrows
    private <T> void updateStatusAtivoRecursively(T entity, Status newStatus, Set<Object> processedEntities, Boolean retrieveDependencies) {
        if (entity != null && !processedEntities.contains(entity)) {
            processedEntities.add(entity); // Adiciona o objeto atual ao conjunto de objetos processados

            Class<?> currentClass = entity.getClass();
            while (currentClass != null) {
                updateAuditFields(entity, newStatus, currentClass);
                currentClass = currentClass.getSuperclass();
            }

            for (Field field : entity.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (isAssociationField(field)) {
                    Object fieldValue = field.get(entity);
                    if (fieldValue instanceof Collection<?> collection) {
                        if(retrieveDependencies){
                            collection.forEach(subEntity -> updateStatusAtivoRecursively(subEntity, newStatus, new HashSet<>(processedEntities), true)); // listas (dependentes)
                        }

                    } else {
                        updateStatus(fieldValue, newStatus); // objetos (dependências)
                    }
                } else if (isManyToManyAssociationField(field)) {
                    Object fieldValue2 = field.get(entity);
                    if (fieldValue2 instanceof Collection<?> collection) {
                        if(retrieveDependencies){
                            collection.forEach(subEntity -> updateStatusAtivoRecursively(subEntity, newStatus, new HashSet<>(processedEntities), true));
                        }

                    }
                }
            }
            removerLixeira(entity);
        }
    }

    @SneakyThrows
    private <T> void updateStatus(T entity, Status newStatus) {
        if (entity != null) {
            Class<?> currentClass = entity.getClass();
            while (currentClass != null) {
                updateAuditFields(entity, newStatus, currentClass);
                currentClass = currentClass.getSuperclass();
            }

            for (Field field : entity.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class)) {
                    Object fieldValue = field.get(entity);
                    updateStatus(fieldValue, newStatus);
                }
            }

            if(newStatus.equals(Status.ACTIVE)){
                removerLixeira(entity);
            }
        }
    }

    @SneakyThrows
    private <T> void updateStatusOtherRecursively(T entity, Status newStatus, Set<Object> processedEntities) {
        if (entity != null && !processedEntities.contains(entity)) {
            processedEntities.add(entity); // Adiciona o objeto atual ao conjunto de objetos processados

            if (newStatus.equals(Status.DELETED)) {
                inserirLixeira(entity);
            }

            Class<?> currentClass = entity.getClass();
            while (currentClass != null) {
                updateAuditFields(entity, newStatus, currentClass);
                currentClass = currentClass.getSuperclass();
            }

            for (Field field : entity.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (isAssociationField(field)) {
                    Object fieldValue = field.get(entity);
                    if (fieldValue instanceof Collection<?> collection) {
                        collection.forEach(subEntity -> updateStatusOtherRecursively(subEntity, newStatus, new HashSet<>(processedEntities))); // listas (dependentes)
                    }
                } else if (isManyToManyAssociationField(field)) {
                    Object fieldValue = field.get(entity);
                    if (fieldValue instanceof Collection<?> collection) {
                        collection.forEach(subEntity -> updateStatusOtherRecursively(subEntity, newStatus, new HashSet<>(processedEntities)));
                    }
                }
            }
        }
    }

    private boolean isAssociationField(Field field) {
        return field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(ManyToOne.class) ||
                field.isAnnotationPresent(OneToOne.class);
    }

    private boolean isManyToManyAssociationField(Field field){
        return field.isAnnotationPresent(ManyToMany.class);
    }

    @SneakyThrows
    private <T> void updateAuditFields(T entity, Status newStatus, Class<?> currentClass) {
        Field statusField = getFieldByName(currentClass);
        if (statusField != null) {
            statusField.setAccessible(true);
            statusField.set(entity, newStatus);
        }
    }

    private <T> void saveEntity(T entity) {
        Class<?> entityType = entity.getClass();
        CrudRepository<T, Long> repository = dynamicRepositoryFactory.createRepository(entityType);
        repository.save(entity);
    }

    private Field getFieldByName(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals("status")) {
                return field;
            }
        }
        return null;
    }

    //************** VALIDAR DEPENDÊNCIAS

    @SneakyThrows
    public <T> void verifyDependenciesStatus(T entity) {
        if (entity != null) {
            for (Field field : getFields(entity.getClass())) {
                field.setAccessible(true);
                if (isManyToOneOrOneToOne(field)) {
                    Object fieldValue = field.get(entity);
                    if(fieldValue != null){
                        verifyDependencyStatus(fieldValue);
                    }
                }
            }
        }
    }

    @SneakyThrows
    private <T> void verifyDependencyStatus(T fieldValue) {
        Class<?> currentClass = fieldValue.getClass();

        for (Field dependencyField : getFields(currentClass)) {
            dependencyField.setAccessible(true);
            if (isIdField(dependencyField)) {
                Object id = dependencyField.get(fieldValue);
                ResolvableType resolvableType = ResolvableType.forClassWithGenerics(Repository.class, currentClass, Long.class);
                Class<?> entityType = resolvableType.resolveGeneric(0);
                CrudRepository<T, Long> repository = dynamicRepositoryFactory.createRepository(entityType);
                Object obj = repository.findById((Long) id).orElseThrow(() -> new ValidationException("Dependência " + currentClass.getSimpleName() + " não encontrada"));

                validateSituacao(obj);
                break;
            }
        }
    }

    @SneakyThrows
    private <T> void validateSituacao(T obj) {
        Class<?> currentClass = obj.getClass().getSuperclass();

        for (Field field : getFields(currentClass)) {
            if ("status".equals(field.getName())) {
                field.setAccessible(true);
                Object situacaoValue = field.get(obj);
                if (!isValidSituacao(situacaoValue)) {
                    throw new ValidationException("Dependência " + obj.getClass().getSimpleName() + " excluída ou com status nulo");
                }
                break;
            }
        }
    }

    private boolean isValidSituacao(Object situacaoValue) {
        return situacaoValue != null && (situacaoValue.equals(Status.ACTIVE) || situacaoValue.equals(Status.INACTIVE));
    }

    private boolean isManyToOneOrOneToOne(Field field) {
        return field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class);
    }

    private boolean isIdField(Field field) {
        return field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(EmbeddedId.class);
    }

    private Field[] getFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

    //************** LIXEIRA

    @Transactional
    @SneakyThrows
    public <T> void inserirLixeira(T entity) { // chamado pelos métodos para incluir o objeto excluido na tabela Lixeira
        Class<?> currentClass = entity.getClass();
        Class<?> superClass = currentClass.getSuperclass();
        boolean idFound = false;

        Map<String, Object> entidadeIdMap = new HashMap<>();

        for (Field field : getFields(superClass)) {
            field.setAccessible(true);
            if (isIdField(field)) {
                Object id = field.get(entity);
                entidadeIdMap.put(field.getName(), id);
                idFound = true;
                break;
            }
        }
        if(!idFound){
            for (Field field : getFields(currentClass)) {
                field.setAccessible(true);
                if (isIdField(field)) {
                    Object id = field.get(entity);
                    entidadeIdMap.put(field.getName(), id);
                    break;
                }
            }
        }

        if(trashRepository.existsByEntityId(entidadeIdMap)){
            Trash trash = trashRepository.findByEntityId(entidadeIdMap);
            trash.setDate(LocalDateTime.now());
            trash.setUsername(customUserUtil.getLoggedUsername());
            trash.setStatus(Status.DELETED);
            trashRepository.save(trash);
        } else{
            Trash trash = new Trash();
            trash.setDate(LocalDateTime.now());
            trash.setTableName(entity.getClass().getSimpleName());
            trash.setReferencedTable(String.valueOf(entity.getClass()));
            trash.setUsername(customUserUtil.getLoggedUsername());
            trash.setStatus(Status.DELETED);
            trash.setEntityId(entidadeIdMap);
            trashRepository.save(trash);
        }
    }

    @Transactional
    @SneakyThrows
    public <T> void removerLixeira(T entity) { // chamado pelos métodos para remover o objeto recuperado da tabela Lixeira
        Class<?> currentClass = entity.getClass();
        Class<?> superClass = currentClass.getSuperclass();
        boolean idFound = false;
        Map<String, Object> entidadeIdMap = new HashMap<>();

        for (Field field : getFields(superClass)) {
            field.setAccessible(true);
            if (isIdField(field)) {
                Object id = field.get(entity);
                entidadeIdMap.put(field.getName(), id);
                idFound = true;
                break;
            }
        }
        if(!idFound){
            for (Field field : getFields(currentClass)) {
                field.setAccessible(true);
                if (isIdField(field)) {
                    Object id = field.get(entity);
                    entidadeIdMap.put(field.getName(), id);
                    break;
                }
            }
        }


        Trash trash = trashRepository.findByEntityId(entidadeIdMap);
        if(trash != null){
            trashRepository.delete(trash);
        }
    }

    @Transactional
    public <T> void retrieve(Long id, Boolean retrieveDependencies) { // método para reativar o item da lixeira, bem como seus dependentes (listas)
        if(trashRepository.existsById(id)){

            Trash trash = trashRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
            String entityClassName = trash.getReferencedTable().replace("class ", "");
            Map<String, Object> entityIds = trash.getEntityId();

            Class<T> entityType;
            try {
                entityType = (Class<T>) Class.forName(entityClassName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Tipo de entidade não encontrado: " + entityClassName, e);
            }
            CrudRepository<T, Long> repository = dynamicRepositoryFactory.createRepository(entityType);

            for (Map.Entry<String, Object> entry : entityIds.entrySet()) {
                Object fieldValue = entry.getValue();
                Long entityId = fieldValue instanceof Integer ? ((Integer) fieldValue).longValue() : (Long) fieldValue;
                T entity = repository.findById(entityId).orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada com ID: " + entityId));

                updateStatusAtivoRecursively(entity, Status.ACTIVE, new HashSet<>(), retrieveDependencies);
            }

        }
    }

}
