package br.com.todeschini.libspecificationhandler;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;

/**
 * findAll method
 * Genereic class that contains the query mounting logic, according to the requested columns, operations, and values.
 */
public class SpecificationHelper<T> {

    public Specification<T> buildSpecification(List<String> columns, List<String> operations, List<String> values) {
        Specification<T> specification = null;

        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            String operation = operations.size() > i ? operations.get(i) : operations.get(0);
            String value = values.size() > i ? values.get(i) : values.get(0);

            if (column.contains(".")) {
                String[] parts = column.split("\\.");
                String relatedField = parts[parts.length - 1];

                Specification<T> spec = (root, query, builder) -> {
                    Join<?, ?> join = null;

                    // Joins util the last field
                    for (int j = 0; j < parts.length - 1; j++) {
                        join = Objects.requireNonNullElse(join, root).join(parts[j]);
                    }

                    // Adding support for different operations
                    return switch (operation) {
                        case ":" -> builder.equal(Objects.requireNonNull(join).get(relatedField), value);
                        case "!=" -> builder.notEqual(Objects.requireNonNull(join).get(relatedField), value);
                        case ">" -> builder.greaterThan(Objects.requireNonNull(join).get(relatedField), value);
                        case "<" -> builder.lessThan(Objects.requireNonNull(join).get(relatedField), value);
                        case ">=" -> builder.greaterThanOrEqualTo(Objects.requireNonNull(join).get(relatedField), value);
                        case "<=" -> builder.lessThanOrEqualTo(Objects.requireNonNull(join).get(relatedField), value);
                        case "=" -> builder.like(
                                builder.function("unaccent", String.class, builder.upper(Objects.requireNonNull(join).get(relatedField).as(String.class))),
                                builder.function("unaccent", String.class, builder.literal("%" + value.toUpperCase() + "%"))
                        );
                        default -> throw new UnsupportedOperationException("Operation not supported: " + operation);
                    };
                };

                if (specification == null) {
                    specification = Specification.where(spec);
                } else {
                    specification = values.size() > 1 ? specification.and(spec) : specification.or(spec);
                }
            } else {
                // If is a main entity column, it's used the existing logic
                Specification<T> spec = new SearchSpecificationImpl<>(new SearchCriteria(column, operation, value));

                if (specification == null) {
                    specification = Specification.where(spec);
                } else {
                    specification = values.size() > 1 ? specification.and(spec) : specification.or(spec);
                }
            }
        }

        return specification;
    }
}
