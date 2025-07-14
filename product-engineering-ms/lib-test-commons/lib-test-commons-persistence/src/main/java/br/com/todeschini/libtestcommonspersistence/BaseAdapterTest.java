package br.com.todeschini.libtestcommonspersistence;

import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import org.junit.jupiter.api.Test;

public abstract class BaseAdapterTest<E, D> {

    protected abstract Convertable<E, D> getAdapter();

    protected abstract D buildDomain();

    protected abstract E buildEntity();

    @Test
    void testToEntity() {
        D domain = buildDomain();
        E entity = getAdapter().toEntity(domain);
        assertDomainToEntity(domain, entity);
    }

    @Test
    void testToDomain() {
        E entity = buildEntity();
        D domain = getAdapter().toDomain(entity);
        assertEntityToDomain(entity, domain);
    }

    protected abstract void assertDomainToEntity(D domain, E entity);

    protected abstract void assertEntityToDomain(E entity, D domain);
}
