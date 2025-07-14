package br.com.todeschini.libauditwebapi.service;

import br.com.todeschini.libauditpersistence.repositories.DynamicRepositoryFactory;
import br.com.todeschini.libauditwebapi.metadata.Audit;
import br.com.todeschini.libauditwebapi.model.AuditEntry;
import br.com.todeschini.libauthservicewebapi.utils.CustomUserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.*;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditService auditService;
    private final CustomUserUtil customUserUtil;
    private final DynamicRepositoryFactory dynamicRepositoryFactory;
    private final ObjectMapper objectMapper;
    private final Environment environment;

    @PostConstruct
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Around("@annotation(audit)")
    public Object around(ProceedingJoinPoint joinPoint, Audit audit) throws Throwable {
        // disable on test profile
        if (List.of(environment.getActiveProfiles()).contains("test")) {
            return joinPoint.proceed();
        }

        Object[] args = joinPoint.getArgs();
        List<AuditEntry> entries = new ArrayList<>();
        String user = getCurrentUser();

        Map<String, Object> beforeMap = new HashMap<>();

        if (args.length > 0) {
            Object dto = args[0];

            if (dto instanceof List<?> list && !list.isEmpty() && list.get(0) instanceof Number) {
                for (Object idObj : list) {
                    Long id = extractId(idObj);
                    if (id != null) {
                        Object before = deepClone(findEntityById(id, audit.entityClass()), audit.entityClass());
                        beforeMap.put(id.toString(), before);
                    }
                }
            } else {
                Long id = extractId(dto);
                if (id != null) {
                    Object before = deepClone(findEntityById(id, audit.entityClass()), audit.entityClass());
                    beforeMap.put(id.toString(), before);
                }
            }
        }

        Object result = joinPoint.proceed();

        if (!beforeMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : beforeMap.entrySet()) {
                Long id = Long.parseLong(entry.getKey());
                Object after = findEntityById(id, audit.entityClass());
                Object afterClone = deepClone(after, audit.entityClass());

                if (entry.getValue() != null || afterClone != null) {
                    entries.add(AuditEntry.builder()
                            .service(audit.service())
                            .entity(audit.entity())
                            .entityId(entry.getKey())
                            .operation(audit.operation())
                            .user(user)
                            .before(entry.getValue())
                            .after(afterClone)
                            .timestamp(Instant.now())
                            .build());
                }
            }
        } else {
            Object body = extractResponseBody(result);
            Long id = extractId(body);
            Object after = (id != null) ? findEntityById(id, audit.entityClass()) : null;
            String entityId = (id != null) ? id.toString() : "unknown";

            entries.add(AuditEntry.builder()
                    .service(audit.service())
                    .entity(audit.entity())
                    .entityId(entityId)
                    .operation(audit.operation())
                    .user(user)
                    .before(null)
                    .after(after)
                    .timestamp(Instant.now())
                    .build());
        }

        entries.forEach(auditService::log);
        return result;
    }

    private Long extractId(Object input) {
        try {
            if (input instanceof Long) return (Long) input;
            if (input instanceof Integer) return ((Integer) input).longValue();
            if (input instanceof String) return Long.parseLong((String) input);

            Field field = input.getClass().getDeclaredField("id");
            field.setAccessible(true);
            Object idValue = field.get(input);
            if (idValue instanceof Long) return (Long) idValue;
            if (idValue instanceof Integer) return ((Integer) idValue).longValue();
            return idValue != null ? Long.parseLong(idValue.toString()) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private <T> T deepClone(Object original, Class<T> clazz) {
        try {
            if (original == null) return null;
            String json = objectMapper.writeValueAsString(original);
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deep clone object", e);
        }
    }

    private Object findEntityById(Long id, Class<?> entityClass) {
        var repository = dynamicRepositoryFactory.createRepository(entityClass);
        return repository.findById(id).orElse(null);
    }

    private String getCurrentUser() {
        return Optional.ofNullable(customUserUtil.getLoggedUsername()).orElse("anonymous");
    }

    private Object extractResponseBody(Object result) {
        try {
            if (result instanceof ResponseEntity<?> responseEntity) {
                return responseEntity.getBody();
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
