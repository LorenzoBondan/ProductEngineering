package br.com.todeschini.persistence.util;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public class DynamicRepositoryFactory {

    @PersistenceContext
    private EntityManager entityManager;

    public <T, ID extends Serializable> JpaRepository<T, ID> createRepository(Class<?> entityClass) {

        JpaEntityInformation<T, ?> entityInformation = JpaEntityInformationSupport.getEntityInformation((Class<T>) entityClass, entityManager);

        return new JpaRepository<T, ID>() {
            @Override
            public <S extends T> S save(S entity) {
                Assert.notNull(entity, "Entity must not be null");
                if (entityInformation.isNew(entity)) {
                    entityManager.persist(entity);
                    return entity;
                } else {
                    return entityManager.merge(entity);
                }
            }

            @Override
            public Optional<T> findById(ID id) {
                Class<T> entityType = (Class<T>) entityClass;
                T entity = entityManager.find(entityType, id);
                return Optional.ofNullable(entity);
            }

            @Override
            public <S extends T> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public List<T> findAll(Sort sort) {
                return null;
            }

            @Override
            public Page<T> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends T> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<T> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public T getOne(ID id) {
                return null;
            }

            @Override
            public <S extends T> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends T> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends T> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends T> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public boolean existsById(ID id) {
                return false;
            }

            @Override
            public List<T> findAll() {
                return null;
            }

            @Override
            public List<T> findAllById(Iterable<ID> ids) {
                return null;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(ID id) {

            }

            @Override
            public void delete(T entity) {

            }

            @Override
            public void deleteAll(Iterable<? extends T> entities) {

            }

            @Override
            public void deleteAll() {

            }
        };
    }
}
