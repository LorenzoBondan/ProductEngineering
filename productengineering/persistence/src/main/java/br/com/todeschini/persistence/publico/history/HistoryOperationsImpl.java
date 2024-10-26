package br.com.todeschini.persistence.publico.history;

import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.business.publico.history.spi.HistoryOperations;
import br.com.todeschini.persistence.entities.enums.MappableEnum;
import br.com.todeschini.persistence.entities.publico.History;
import br.com.todeschini.persistence.util.DynamicRepositoryFactory;
import br.com.todeschini.persistence.util.ReflectionUtils;
import br.com.todeschini.persistence.util.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementação dos serviços de operações de histórico.
 * Esta classe gerencia as operações relacionadas ao versionamento de entidades,
 * convertendo registros históricos armazenados em JSON para objetos de entidade e vice-versa.
 */
@Component
public class HistoryOperationsImpl implements HistoryOperations {

    private final HistoryRepository repository;
    private final ObjectMapper objectMapper;
    private final DynamicRepositoryFactory dynamicRepositoryFactory;

    public HistoryOperationsImpl(HistoryRepository repository, ObjectMapper objectMapper, DynamicRepositoryFactory dynamicRepositoryFactory) {
        this.repository = repository;
        this.objectMapper = objectMapper;
        this.dynamicRepositoryFactory = dynamicRepositoryFactory;
    }

    /**
     * Recupera uma lista de históricos de uma entidade baseada no ID fornecido.
     *
     * @param <T> Tipo da entidade a ser recuperada.
     * @param entityType Tipo da entidade para a qual os históricos são recuperados.
     * @param tabname Nome da tabela correspondente à entidade.
     * @param recordId ID do registro cujo histórico será pesquisado.
     * @param attributeMappings Mapeamentos que relacionam campos com suas respectivas classes de entidade (somente dependências)
     * @return Uma lista de objetos DHistory contendo o histórico da entidade.
     */
    @Override
    @Transactional(readOnly = true)
    public <T> List<DHistory<T>> getHistoryEntityByRecord(Class<T> entityType, String tabname, String recordId, Map<String, Class<?>> attributeMappings) {
        String idFieldName = StringUtils.toSnakeCase(ReflectionUtils.getIdFieldName(entityType));

        List<History> histories = repository.findByTabnameAndRecordId(tabname, idFieldName, recordId);

        return histories.stream()
                .map(history -> new DHistory<>(
                        history.getId(),
                        history.getTstamp(),
                        retirarAutorDoMap(history.getOldVal()),
                        convertToEntity(history.getOldVal(), entityType, attributeMappings)))
                .collect(Collectors.toList());
    }

    private String retirarAutorDoMap(Map<String, Object> oldVal) {
        if (oldVal != null && oldVal.containsKey("modificadopor")) {
            return oldVal.get("modificadopor").toString();
        }
        return null;
    }

    /**
     * Converte um JSON de histórico para uma entidade do tipo especificado.
     *
     * @param <T> Tipo da entidade a ser convertida.
     * @param oldVal JSON contendo os campos da entidade.
     * @param entityType Tipo da entidade para a qual o JSON será convertido.
     * @param attributeMappings Mapeamentos que relacionam campos com suas respectivas classes de entidade.
     * @return Um objeto da entidade convertido a partir do JSON.
     */
    private <T> T convertToEntity(Map<String, Object> oldVal, Class<T> entityType, Map<String, Class<?>> attributeMappings) {
        try {
            JsonNode node = objectMapper.valueToTree(oldVal);
            JsonNode transformedNode = transformNode(node, attributeMappings, entityType);
            JsonNode camelCaseNode = convertSnakeToCamel(transformedNode);

            return objectMapper.treeToValue(camelCaseNode, entityType);
        } catch (Exception e) {
            throw new RuntimeException("Error converting Map to entity: " + entityType.getSimpleName(), e);
        }
    }

    /**
     * Transforma um nó JSON, detectando e convertendo campos que representam IDs de entidades relacionadas.
     *
     * @param node Nó JSON a ser transformado.
     * @param attributeMappings Mapeamentos que relacionam campos com suas respectivas classes de entidade.
     * @param entityType Tipo da entidade atual que está sendo processada.
     * @return Um novo nó JSON com os campos transformados, substituindo IDs por representações completas de entidades.
     * @throws IllegalAccessException Se houver falha ao acessar os campos da entidade.
     */
    private JsonNode transformNode(JsonNode node, Map<String, Class<?>> attributeMappings, Class<?> entityType) throws IllegalAccessException {
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode newNode = mapper.createObjectNode();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> fieldEntry = fields.next();
            String fieldName = fieldEntry.getKey();
            JsonNode fieldValue = fieldEntry.getValue();

            // Se o campo não for nulo e estiver no mapeamento de atributos, converte o código para a entidade correspondente
            if (!fieldValue.isNull() && attributeMappings.containsKey(fieldName)) {
                Class<?> relatedEntityClass = attributeMappings.get(fieldName);
                Integer code = fieldValue.asInt();
                Object relatedEntity = findEntityByCode(relatedEntityClass, code);

                // Criar um novo nó para o objeto relacionado
                ObjectNode relatedNode = transformEntityToNode(relatedEntity, attributeMappings, new HashSet<>()); // Passa um novo HashSet para controlar os ciclos

                // Usa o nome do campo em vez do nome da entidade para evitar conflitos
                String nodeName = StringUtils.convertToCamelCase(fieldName);
                newNode.set(nodeName, relatedNode);

            } else {
                // Verifica se o campo é um enum que mapeia um valor inteiro
                Field enumField = findFieldByName(entityType, StringUtils.toCamelCase(fieldName));

                if (enumField != null && enumField.getType().isEnum()) {
                    // Solução para o problema de tipos
                    @SuppressWarnings("unchecked")
                    Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>) enumField.getType();

                    // Solução para o problema de incompatibilidade de tipos
                    Enum<?> enumValue = convertToEnumWildcard(enumClass, fieldValue.asInt());
                    newNode.set(fieldName, mapper.valueToTree(enumValue));
                } else {
                    newNode.set(fieldName, fieldValue);
                }
            }
        }

        return newNode;
    }

    // Método auxiliar para converter um valor inteiro em um enum correspondente
    private <E extends Enum<E>> E convertToEnum(Class<E> enumClass, int value) {
        for (E enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant instanceof MappableEnum && ((MappableEnum) enumConstant).getValue() == value) {
                return enumConstant;
            }
        }
        return null;
    }

    // Método para lidar com a captura de wildcard
    private <E extends Enum<E>> E convertToEnumWildcard(Class<? extends Enum<?>> enumClass, int value) {
        @SuppressWarnings("unchecked")
        Class<E> castedEnumClass = (Class<E>) enumClass;
        return convertToEnum(castedEnumClass, value);
    }

    /**
     * Converte uma entidade em um nó JSON, ignorando campos de auditoria e listas.
     * Se um campo contém uma entidade relacionada, mapeia essa entidade recursivamente.
     *
     * @param entity Entidade a ser convertida em um nó JSON.
     * @param attributeMappings Mapeamentos que relacionam campos com suas respectivas classes de entidade.
     * @param processedEntities Conjunto de entidades já processadas para detecção de ciclos.
     * @return Um nó JSON representando a entidade e suas entidades relacionadas.
     * @throws IllegalAccessException Se houver falha ao acessar os campos da entidade.
     */
    private ObjectNode transformEntityToNode(Object entity, Map<String, Class<?>> attributeMappings, Set<Object> processedEntities) throws IllegalAccessException {
        ObjectMapper mapper = new ObjectMapper();

        // Registre o módulo JavaTimeModule para suportar as classes de data/hora do Java 8
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // para serializar datas em formato ISO-8601

        ObjectNode node = mapper.createObjectNode();

        List<String> auditFields = Arrays.asList("modificadoem", "criadoem", "criadopor", "modificadopor");

        // Verifica se a entidade já foi processada, para evitar stackoverflow em caso de autorrelacionamentos
        if (processedEntities.contains(entity)) {
            // Evita ciclos adicionando uma referência ou um indicador específico
            node.put("cicloDetectado", "true");
            return node;
        }

        // Marca a entidade como processada
        processedEntities.add(entity);

        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();

            if (!auditFields.contains(fieldName) && !Collection.class.isAssignableFrom(field.getType())) {
                Object value = field.get(entity);
                if (value != null) {
                    // Se o valor é uma instância de uma classe que precisa ser mapeada, faça a chamada recursiva
                    if (attributeMappings.containsValue(value.getClass())) {
                        // Converte o valor usando a recursão
                        ObjectNode relatedNode = transformEntityToNode(value, attributeMappings, processedEntities);
                        // A chave aqui deve ser o nome da entidade, não o código
                        String entityName = value.getClass().getSimpleName().toLowerCase();
                        node.set(entityName, relatedNode);
                    } else if (value instanceof Period period) {
                        // Verifica se o valor é do tipo Period e converte para uma string legível
                        String periodString = period.getYears() + " years " + period.getMonths() + " months " + period.getDays() + " days";
                        node.put(StringUtils.toSnakeCase(fieldName), periodString.trim());
                    } else {
                        // Se não for uma entidade nem um atributo Period, apenas adiciona o valor ao nó
                        node.set(StringUtils.toSnakeCase(fieldName), mapper.valueToTree(value));
                    }
                }
            }
        }

        // Remove a entidade da lista de processados ao final do processamento
        processedEntities.remove(entity);

        return node;
    }

    private Field findFieldByName(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private JsonNode convertSnakeToCamel(JsonNode originalNode) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode transformedNode = mapper.createObjectNode();

        originalNode.fields().forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            JsonNode fieldValue = entry.getValue();

            // Converte o nome do campo para camelCase
            String camelCaseName = StringUtils.toCamelCase(fieldName);

            // Se o valor for um nó de objeto, aplica a transformação recursivamente
            if (fieldValue.isObject()) {
                transformedNode.set(camelCaseName, convertSnakeToCamel(fieldValue));
            } else {
                transformedNode.set(camelCaseName, fieldValue);
            }
        });

        return transformedNode;
    }

    /**
     * Encontra uma entidade pelo seu código.
     *
     * @param <T> Tipo da entidade a ser encontrada.
     * @param entityClass Classe da entidade a ser encontrada.
     * @param code Código da entidade.
     * @return A entidade encontrada.
     * @throws RuntimeException Se a entidade não for encontrada ou se ocorrer uma falha durante a busca.
     */
    private <T> T findEntityByCode(Class<T> entityClass, Integer code) {
        try {
            CrudRepository<T, Integer> repository = dynamicRepositoryFactory.createRepository(entityClass);
            return repository.findById(code)
                    .orElseThrow(() -> new RuntimeException(entityClass.getSimpleName() + " não encontrado(a) com o código: " + code));
        } catch (Exception e) {
            throw new RuntimeException("Falha ao encontrar entidade " + entityClass.getSimpleName() + " com o código: " + code, e);
        }
    }
}
