package br.com.todeschini.persistence.util;

public interface Convertable<E, D>{
    E toEntity(D domain);
    D toDomain(E entity);
}
