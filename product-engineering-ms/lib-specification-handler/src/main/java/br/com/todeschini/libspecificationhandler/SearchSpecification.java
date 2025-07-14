package com.projects.libspecificationhandler;

import org.springframework.data.jpa.domain.Specification;

/**
 * findAll method
 * Generic interface that extends the standard Spring Specification interface.
 */
public interface SearchSpecification<T> extends Specification<T> {
}
