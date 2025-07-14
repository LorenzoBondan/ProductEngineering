package br.com.todeschini.libvalidationhandler.contracts;

public interface Convertable<E, D>{
    E toEntity(D domain);
    D toDomain(E entity);
}
