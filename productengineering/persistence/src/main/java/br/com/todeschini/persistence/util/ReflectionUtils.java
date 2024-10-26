package br.com.todeschini.persistence.util;

import javax.persistence.Id;
import java.lang.reflect.Field;

public class ReflectionUtils {

    public static String getIdFieldName(Class<?> clazz) {
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    return field.getName();
                }
            }
            clazz = clazz.getSuperclass();
        }
        throw new RuntimeException("Nenhum campo mapeado com @Id na classe ou em suas superclasses");
    }
}

