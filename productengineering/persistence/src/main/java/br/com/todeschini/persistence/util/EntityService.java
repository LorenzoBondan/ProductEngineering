package br.com.todeschini.persistence.util;

import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.domain.exceptions.ValidationException;
import br.com.todeschini.domain.metadata.BatchEditable;
import br.com.todeschini.persistence.entities.enums.Situacao;
import br.com.todeschini.persistence.entities.publico.Lixeira;
import br.com.todeschini.persistence.publico.lixeira.LixeiraRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
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
    private LixeiraRepository lixeiraRepository;

    @Transactional
    @SneakyThrows
    public <T> void changeStatusToAtivo(T entity, Situacao situacao, Boolean retrieveDependencies) {
        updateStatusAtivoRecursively(entity, situacao, new HashSet<>(), retrieveDependencies);
        saveEntity(entity);
    }

    @Transactional
    @SneakyThrows
    public <T> void changeStatusToOther(T entity, Situacao situacao) {
        updateStatusOtherRecursively(entity, situacao, new HashSet<>());
        saveEntity(entity);
    }

    @SneakyThrows
    private <T> void updateStatusAtivoRecursively(T entity, Situacao newStatus, Set<Object> processedEntities, Boolean retrieveDependencies) {
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
    private <T> void updateStatus(T entity, Situacao newStatus) {
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

            if(newStatus.equals(Situacao.ATIVO)){
                removerLixeira(entity);
            }
        }
    }

    @SneakyThrows
    private <T> void updateStatusOtherRecursively(T entity, Situacao newStatus, Set<Object> processedEntities) {
        if (entity != null && !processedEntities.contains(entity)) {
            processedEntities.add(entity); // Adiciona o objeto atual ao conjunto de objetos processados

            if (newStatus.equals(Situacao.LIXEIRA)) {
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
    private <T> void updateAuditFields(T entity, Situacao newStatus, Class<?> currentClass) {
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

        for (Field field : getFields(currentClass)) {
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
        return situacaoValue.equals(Situacao.ATIVO) || situacaoValue.equals(Situacao.INATIVO);
    }

    private boolean isManyToOneOrOneToOne(Field field) {
        return field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class);
    }

    private boolean isIdField(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private Field[] getFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

    //************** LIXEIRA

    @Transactional
    @SneakyThrows
    public <T> void inserirLixeira(T entity) { // chamado pelos métodos para incluir o objeto excluido na tabela Lixeira
        Class<?> currentClass = entity.getClass();

        Map<String, Object> entidadeIdMap = new HashMap<>();

        for (Field field : getFields(currentClass)) {
            field.setAccessible(true);
            if (isIdField(field)) {
                Object id = field.get(entity);
                entidadeIdMap.put(field.getName(), id);
                break;
            }
        }

        String loggedUser = customUserUtil.getLoggedUsername();
        if(loggedUser.length() > 50){
            loggedUser = loggedUser.substring(0, 50);
        }

        if(lixeiraRepository.existsByEntidadeid(entidadeIdMap)){
            Lixeira lixeira = lixeiraRepository.findByEntidadeid(entidadeIdMap);
            lixeira.setData(LocalDateTime.now());
            lixeira.setUsuario(loggedUser);
            lixeira.setSituacao(Situacao.LIXEIRA);
            lixeiraRepository.save(lixeira);
        } else{
            Lixeira lixeira = new Lixeira();
            lixeira.setData(LocalDateTime.now());
            lixeira.setNometabela(entity.getClass().getSimpleName());
            lixeira.setTabela(String.valueOf(entity.getClass()));
            lixeira.setUsuario(loggedUser);
            lixeira.setSituacao(Situacao.LIXEIRA);
            lixeira.setEntidadeid(entidadeIdMap);
            lixeiraRepository.save(lixeira);
        }
    }

    @Transactional
    @SneakyThrows
    public <T> void removerLixeira(T entity) { // chamado pelos métodos para remover o objeto recuperado da tabela Lixeira
        Class<?> currentClass = entity.getClass();
        Map<String, Object> entidadeIdMap = new HashMap<>();

        for (Field field : getFields(currentClass)) {
            field.setAccessible(true);
            if (isIdField(field)) {
                Object id = field.get(entity);
                entidadeIdMap.put(field.getName(), id);
                break;
            }
        }

        Lixeira lixeira = lixeiraRepository.findByEntidadeid(entidadeIdMap);
        if(lixeira != null){
            lixeiraRepository.delete(lixeira);
        }
    }

    @Transactional
    public <T> void recuperarDaLixeira(Integer id, Boolean retrieveDependencies) { // método para reativar o item da lixeira, bem como seus dependentes (listas)
        if(lixeiraRepository.existsById(id)){

            Lixeira lixeira = lixeiraRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id));
            String entityClassName = lixeira.getTabela().replace("class ", "");
            Map<String, Object> entityIds = lixeira.getEntidadeid();

            Class<T> entityType;
            try {
                entityType = (Class<T>) Class.forName(entityClassName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Tipo de entidade não encontrado: " + entityClassName, e);
            }
            CrudRepository<T, Integer> repository = dynamicRepositoryFactory.createRepository(entityType);

            for (Map.Entry<String, Object> entry : entityIds.entrySet()) {
                Object fieldValue = entry.getValue();
                Integer entityId = fieldValue instanceof Integer ? ((Integer) fieldValue).intValue() : (Integer) fieldValue;
                T entity = repository.findById(entityId).orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada com ID: " + entityId));

                updateStatusAtivoRecursively(entity, Situacao.ATIVO, new HashSet<>(), retrieveDependencies);
            }

        }
    }

    /**
     * Obter uma lista de atributos de uma entidade que são anotados com a anotação de edição em lote
     */
    public List<String> obterAtributosEditaveis(Class<?> currentClass) {
        List<String> atributosEditaveis = new ArrayList<>();

        // enquanto houver uma superclasse, percorre a hierarquia
        while (currentClass != null && currentClass != Object.class) {
            Field[] fields = currentClass.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(BatchEditable.class)) {
                    atributosEditaveis.add(field.getName());
                }
            }

            currentClass = currentClass.getSuperclass();
        }

        return atributosEditaveis;
    }

}
