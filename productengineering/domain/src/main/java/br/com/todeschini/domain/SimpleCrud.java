package br.com.todeschini.domain;

import java.util.List;

/**
 * Métodos padrão para CRUDs.
 * @param <T> é a entidade
 * @param <J> é a PK da entidade
 */
public interface SimpleCrud<T, J>{

    T insert(T obj);

    T update(J id, T obj);

    void delete(J obj);

    T find(J obj);

    void inactivate(J obj);

    List<T> findAllActiveAndCurrentOne(J obj);
}
