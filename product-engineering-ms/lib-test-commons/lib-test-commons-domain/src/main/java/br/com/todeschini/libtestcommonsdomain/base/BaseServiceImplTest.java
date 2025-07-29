package br.com.todeschini.libtestcommonsdomain.base;

import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public abstract class BaseServiceImplTest<T, ID> {

    protected SimpleCrud<T, ID> crud;

    // Function used to retrieve potential duplicates (e.g., findByName, findByLotCodeAndProduct, etc.)
    protected Function<T, List<T>> findDuplicatesFunc;

    // Predicate to determine if a retrieved item is a duplicate of the candidate (e.g., by comparing ID or name)
    protected Predicate<T> isDuplicatedPredicate;

    // Combines the functions above to determine whether a given object is a duplicate
    protected Predicate<T> isDuplicated;

    // Expected exception type when duplication is detected
    protected Class<? extends RuntimeException> expectedDuplicateException;

    protected T sample;
    protected Object service;

    protected abstract T createSample();
    protected abstract SimpleCrud<T, ID> mockCrud();
    protected abstract ID getExistingId();
    protected abstract T createDifferentSampleWithSameName();
    protected abstract String getName(T obj);
    protected abstract ID getId(T obj);
    protected abstract Object createService(SimpleCrud<T, ID> crud);

    @BeforeEach
    void setupBase() {
        this.crud = mockCrud();
        this.sample = createSample();
        this.service = createService(crud);
        setupDuplicateCheck();
    }

    /**
     * Sets up the isDuplicated predicate using findDuplicatesFunc and isDuplicatedPredicate.
     * Can be overridden for custom duplication logic.
     */
    protected void setupDuplicateCheck() {
        this.isDuplicated = candidate -> {
            List<T> duplicates = findDuplicatesFunc.apply(candidate);
            return duplicates.stream().anyMatch(existing -> isDuplicatedPredicate.test(existing));
        };
    }

    /**
     * Override this in concrete test classes to mock how duplicates are retrieved.
     * Receives a duplicate object that should be returned by the mocked method.
     */
    protected void whenDuplicateCheck(T duplicate) {
        // Default: do nothing
    }

    @Test
    void find_shouldReturn_whenExists() {
        when(crud.buscar(getExistingId())).thenReturn(sample);

        T result = invokeFind(getExistingId());

        assertNotNull(result);
        assertEquals(getName(sample), getName(result));
    }

    @Test
    void findAll_shouldReturnPaged() {
        PageableRequest request = new PageableRequest();
        Paged<T> paged = new Paged<>();
        paged.setContent(singletonList(sample));

        when(crud.buscar(request)).thenReturn(paged);

        Paged<T> result = invokeFindAll(request);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void create_shouldSucceed_whenNotDuplicated() {
        whenDuplicateCheck(null); // Optional: clear mocks
        when(crud.incluir(sample)).thenReturn(sample);

        T result = invokeCreate(sample);

        assertNotNull(result);
        verify(crud).incluir(sample);
    }

    @Test
    void create_shouldThrow_whenDuplicateExists() {
        T duplicate = createDifferentSampleWithSameName();
        whenDuplicateCheck(duplicate);

        assertTrue(isDuplicated.test(sample));

        assertThrows(expectedDuplicateException, () -> invokeCreate(sample));
        verify(crud, never()).incluir(any());
    }

    @Test
    void update_shouldSucceed_whenNotDuplicated() {
        whenDuplicateCheck(null);
        when(crud.atualizar(sample)).thenReturn(sample);

        T result = invokeUpdate(sample);

        assertNotNull(result);
        verify(crud).atualizar(sample);
    }

    @Test
    void update_shouldThrow_whenDuplicateExists() {
        T duplicate = createDifferentSampleWithSameName();
        whenDuplicateCheck(duplicate);

        assertTrue(isDuplicated.test(sample));

        assertThrows(expectedDuplicateException, () -> invokeCreate(sample));
        verify(crud, never()).atualizar(any());
    }

    @Test
    void delete_shouldCallCrud() {
        doNothing().when(crud).excluir(getExistingId());

        invokeDelete(getExistingId());

        verify(crud).excluir(getExistingId());
    }

    protected abstract T invokeFind(ID id);
    protected abstract Paged<T> invokeFindAll(PageableRequest request);
    protected abstract T invokeCreate(T obj);
    protected abstract T invokeUpdate(T obj);
    protected abstract void invokeDelete(ID id);
}
