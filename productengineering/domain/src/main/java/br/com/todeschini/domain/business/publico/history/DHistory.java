package br.com.todeschini.domain.business.publico.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class DHistory<T> {

    private Integer id;
    private LocalDateTime date;
    private String author;
    private T entity;

    public DHistory(Integer id, T entity) {
        this.id = id;
        this.entity = entity;
    }
}
