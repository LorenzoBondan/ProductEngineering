package br.com.todeschini.libtestcommonspersistence;

import br.com.todeschini.libexceptionhandler.exceptions.DatabaseException;
import br.com.todeschini.libexceptionhandler.exceptions.ResourceNotFoundException;
import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class BaseCrudTest<T, ID> {

    protected abstract SimpleCrud<T, ID> getCrud();
    protected abstract T createSample();
    protected abstract ID getExistingId();
    protected abstract ID getNonExistingId();
    protected abstract void mockFindAll();
    protected abstract void mockFindById(ID id);
    protected abstract void mockFindByIdNotFound(ID id);
    protected abstract void mockInsert(T entity);
    protected abstract void mockUpdate(T entity);
    protected abstract void mockUpdateNotFound(T entity);
    protected abstract void mockDelete(ID id);
    protected abstract void mockDeleteWithViolation(ID id);
    protected abstract void mockDeleteNotFound(ID id);
    protected abstract PageableRequest createPageableRequest();

    @Test
    void findAll_shouldReturnPagedResult() {
        mockFindAll();
        PageableRequest request = createPageableRequest();
        Paged<T> result = getCrud().buscar(request);
        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
    }

    @Test
    void find_shouldReturnEntity_whenIdExists() {
        ID id = getExistingId();
        mockFindById(id);
        T result = getCrud().buscar(getExistingId());
        assertNotNull(result);
    }

    @Test
    void find_shouldThrow_whenIdNotFound() {
        ID id = getNonExistingId();
        mockFindByIdNotFound(id);
        assertThrows(ResourceNotFoundException.class, () -> getCrud().buscar(id));
    }

    @Test
    void insert_shouldReturnInsertedEntity() {
        T entity = createSample();
        mockInsert(entity);
        T inserted = getCrud().incluir(entity);
        assertNotNull(inserted);
    }

    @Test
    void update_shouldReturnUpdatedEntity() {
        T entity = createSample();
        mockUpdate(entity);
        T updated = getCrud().atualizar(entity);
        assertNotNull(updated);
    }

    @Test
    void update_shouldThrow_whenIdNotFound() {
        T entity = createSample();
        mockUpdateNotFound(entity);
        assertThrows(ResourceNotFoundException.class, () -> getCrud().atualizar(entity));
    }

    @Test
    void delete_shouldSucceed_whenIdExists() {
        ID id = getExistingId();
        mockDelete(id);
        assertDoesNotThrow(() -> getCrud().excluir(id));
    }

    @Test
    void delete_shouldThrow_whenIdNotFound() {
        ID id = getNonExistingId();
        mockDeleteNotFound(id);
        assertThrows(ResourceNotFoundException.class, () -> getCrud().excluir(id));
    }

    @Test
    void delete_shouldThrow_whenDatabaseViolationOccurs() {
        ID id = getExistingId();
        mockDeleteWithViolation(id);
        assertThrows(DatabaseException.class, () -> getCrud().excluir(id));
    }
}
