package br.com.todeschini.lixeiraservicepersistence;

import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libauditpersistence.repositories.DynamicRepositoryFactory;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.lixeiraservicedomain.events.LixeiraIncluirEvent;
import br.com.todeschini.lixeiraservicedomain.statusupdater.StatusUpdater;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ResolvableType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationEventPublisher;

@Service
@RequiredArgsConstructor
public class EntityService implements StatusUpdater {

    private final DynamicRepositoryFactory dynamicRepositoryFactory;
    private final ApplicationEventPublisher publisher;

    @Transactional
    @SneakyThrows
    public <T> void changeStatusToOther(T entity, DSituacaoEnum situacaoEnum) {
        updateStatusOtherRecursively(entity, situacaoEnum, new HashSet<>());
        saveEntity(entity);
    }

    @SneakyThrows
    @Override
    public <T> void updateStatusAtivoRecursively(T entity, DSituacaoEnum newStatus, Set<Object> processedEntities, Boolean retrieveDependencies) {
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
                        updateStatus(fieldValue, newStatus, processedEntities); // objetos (dependências)
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
            publisher.publishEvent(new LixeiraIncluirEvent<>(entity));
        }
    }

    @SneakyThrows
    private <T> void updateStatus(T entity, DSituacaoEnum newStatus, Set<Object> processedEntities) {
        if (entity != null && !processedEntities.contains(entity)) {
            // Adiciona a entidade ao conjunto de processadas
            processedEntities.add(entity);

            Class<?> currentClass = entity.getClass();
            while (currentClass != null) {
                updateAuditFields(entity, newStatus, currentClass);
                currentClass = currentClass.getSuperclass();
            }

            // Processa os campos da entidade
            for (Field field : entity.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class)) {
                    Object fieldValue = field.get(entity);
                    // Chamada recursiva com controle de entidades processadas
                    updateStatus(fieldValue, newStatus, processedEntities);
                }
            }

            // Remove da lixeira, se aplicável
            if (newStatus.equals(DSituacaoEnum.ATIVO)) {
                publisher.publishEvent(new LixeiraIncluirEvent<>(entity));
            }
        }
    }

    @SneakyThrows
    private <T> void updateStatusOtherRecursively(T entity, DSituacaoEnum newStatus, Set<Object> processedEntities) {
        if (entity != null && !processedEntities.contains(entity)) {
            processedEntities.add(entity); // Adiciona o objeto atual ao conjunto de objetos processados

            if (newStatus.equals(DSituacaoEnum.LIXEIRA)) {
                publisher.publishEvent(new LixeiraIncluirEvent<>(entity));
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
    private <T> void updateAuditFields(T entity, DSituacaoEnum newStatus, Class<?> currentClass) {
        Field statusField = getFieldByName(currentClass);
        if (statusField != null) {
            statusField.setAccessible(true);
            statusField.set(entity, newStatus);
        }
    }

    private <T> void saveEntity(T entity) {
        Class<?> entityType = entity.getClass();
        CrudRepository<T, Integer> repository = dynamicRepositoryFactory.createRepository(entityType);
        repository.save(entity);
    }

    private Field getFieldByName(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals("situacao")) {
                return field;
            }
        }
        return null;
    }

    //************** VALIDAR DEPENDÊNCIAS

    @SneakyThrows
    public <T> void verifyDependenciesStatus(T entity) {
        if (entity != null) {
            Class<?> currentClass = entity.getClass();

            while (currentClass != Object.class) {
                for (Field field : currentClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (isManyToOneOrOneToOne(field)) {
                        Object fieldValue = field.get(entity);
                        if (fieldValue != null) {
                            verifyDependencyStatus(fieldValue);
                        }
                    }
                }
                currentClass = currentClass.getSuperclass();
            }
        }
    }

    @SneakyThrows
    private <T> void verifyDependencyStatus(T fieldValue) {
        Class<?> currentClass = fieldValue.getClass();

        for (Field dependencyField : currentClass.getDeclaredFields()) {
            dependencyField.setAccessible(true);
            if (dependencyField.isAnnotationPresent(Id.class)) {
                Object id = dependencyField.get(fieldValue);
                ResolvableType resolvableType = ResolvableType.forClassWithGenerics(Repository.class, currentClass, Integer.class);
                Class<?> entityType = resolvableType.resolveGeneric(0);
                CrudRepository<T, Integer> repository = dynamicRepositoryFactory.createRepository(entityType);
                Object obj = repository.findById((Integer) id).orElseThrow(() -> new ValidationException("Dependência " + currentClass.getSimpleName() + " não encontrada"));

                validateSituacao(obj);
                break;
            }
        }
    }

    @SneakyThrows
    private <T> void validateSituacao(T obj) {
        Class<?> currentClass = obj.getClass().getSuperclass();

        for (Field field : currentClass.getDeclaredFields()) {
            if ("situacao".equals(field.getName())) {
                field.setAccessible(true);
                Object situacaoValue = field.get(obj);
                if (!isValidSituacao(situacaoValue)) {
                    throw new ValidationException("Dependência " + obj.getClass().getSimpleName() + " na lixeira ou excluída");
                }
                break;
            }
        }
    }

    private boolean isValidSituacao(Object situacaoValue) {
        return situacaoValue.equals(DSituacaoEnum.ATIVO) || situacaoValue.equals(DSituacaoEnum.INATIVO);
    }

    private boolean isManyToOneOrOneToOne(Field field) {
        return field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class);
    }
}
