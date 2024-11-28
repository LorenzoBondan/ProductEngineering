package br.com.todeschini.persistence.util;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.List;
import java.util.Objects;

/**
 * Método buscarTodos
 * Classe genérica que contém a lógica de montagem da query, de acordo com as colunas, operações e valores solicitados
 */
public class SpecificationHelper<T> {

    public Specification<T> buildSpecification(List<String> colunas, List<String> operacoes, List<String> valores) {
        Specification<T> specification = null;

        for (int i = 0; i < colunas.size(); i++) {
            String coluna = colunas.get(i);
            String operacao = operacoes.size() > i ? operacoes.get(i) : operacoes.get(0);
            String valor = valores.size() > i ? valores.get(i) : valores.get(0);

            if (coluna.contains(".")) {
                String[] parts = coluna.split("\\.");
                String relatedField = parts[parts.length - 1];

                Specification<T> spec = (root, query, builder) -> {
                    Join<?, ?> join = null;

                    // Realiza o join até o campo final
                    for (int j = 0; j < parts.length - 1; j++) {
                        join = Objects.requireNonNullElse(join, root).join(parts[j]);
                    }

                    // Adicionando suporte para diferentes operações
                    return switch (operacao) {
                        case ":" -> builder.equal(Objects.requireNonNull(join).get(relatedField), valor);
                        case "!=" -> builder.notEqual(Objects.requireNonNull(join).get(relatedField), valor);
                        case ">" -> builder.greaterThan(Objects.requireNonNull(join).get(relatedField), valor);
                        case "<" -> builder.lessThan(Objects.requireNonNull(join).get(relatedField), valor);
                        case ">=" -> builder.greaterThanOrEqualTo(Objects.requireNonNull(join).get(relatedField), valor);
                        case "<=" -> builder.lessThanOrEqualTo(Objects.requireNonNull(join).get(relatedField), valor);
                        case "=" -> builder.like(
                                builder.function("unaccent", String.class, builder.upper(Objects.requireNonNull(join).get(relatedField).as(String.class))),
                                builder.function("unaccent", String.class, builder.literal("%" + valor.toUpperCase() + "%"))
                        );
                        default -> throw new UnsupportedOperationException("Operação não suportada: " + operacao);
                    };
                };

                if (specification == null) {
                    specification = Specification.where(spec);
                } else {
                    specification = valores.size() > 1 ? specification.and(spec) : specification.or(spec);
                }
            } else {
                // Caso seja uma coluna da entidade principal, usamos a lógica existente
                Specification<T> spec = new SearchSpecificationImpl<>(new SearchCriteria(coluna, operacao, valor));

                if (specification == null) {
                    specification = Specification.where(spec);
                } else {
                    specification = valores.size() > 1 ? specification.and(spec) : specification.or(spec);
                }
            }
        }

        return specification;
    }
}
