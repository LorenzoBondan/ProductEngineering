package br.com.todeschini.persistence.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Método buscarTodos
 * Classe genérica que implementa a lógica de busca das operações
 */
public class SearchSpecificationImpl<T> implements SearchSpecification<T> {

    private final SearchCriteria criteria;

    public SearchSpecificationImpl(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * > maior ou igual a
     * < menor ou igual a
     * = contém
     * : busca exata
     * != diferente de
     */
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            if (isDate(root)) {
                return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue().toString(), DateTimeFormatter.ISO_DATE));
            } else {
                return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
            }
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            if (isDate(root)) {
                return builder.lessThanOrEqualTo(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue().toString(), DateTimeFormatter.ISO_DATE));
            } else {
                return builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
            }
        } else if (criteria.getOperation().equalsIgnoreCase("=")) {
            if (isDate(root)) {
                return builder.equal(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue().toString(), DateTimeFormatter.ISO_DATE));
            } else if (root.get(criteria.getKey()).getJavaType().isEnum()) {
                return builder.equal(root.get(criteria.getKey()), Enum.valueOf((Class<Enum>) root.get(criteria.getKey()).getJavaType(), criteria.getValue().toString()));
            } else {
                return builder.like(
                        builder.function("unaccent", String.class, builder.upper(root.get(criteria.getKey()).as(String.class))),
                        builder.function("unaccent", String.class, builder.literal("%" + criteria.getValue().toString().toUpperCase() + "%"))
                );
            }
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (isDate(root)) {
                return builder.equal(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue().toString()));
            } else if (root.get(criteria.getKey()).getJavaType().isEnum()) {
                return builder.equal(root.get(criteria.getKey()), Enum.valueOf((Class<Enum>) root.get(criteria.getKey()).getJavaType(), criteria.getValue().toString()));
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue().toString());
            }
        } else if (criteria.getOperation().equalsIgnoreCase("!=")) {
            if (root.get(criteria.getKey()).getJavaType().isEnum()) {
                return builder.notEqual(root.get(criteria.getKey()), Enum.valueOf((Class<Enum>) root.get(criteria.getKey()).getJavaType(), criteria.getValue().toString()));
            } else if (isDate(root)) {
                return builder.notEqual(root.get(criteria.getKey()), LocalDate.parse(criteria.getValue().toString(), DateTimeFormatter.ISO_DATE));
            } else {
                return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }

    private boolean isDate(Root<T> root) {
        return root.get(criteria.getKey()).getJavaType() == LocalDate.class || root.get(criteria.getKey()).getJavaType() == LocalDateTime.class;
    }
}
