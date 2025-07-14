package br.com.todeschini.libvalidationhandler.metadata;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DomainService {
}

