package br.com.todeschini.libauditwebapi.metadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Audit {
    String entity();
    String operation();
    String idField() default "id";
    String service();
    Class<?> entityClass();
}
