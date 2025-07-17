package br.com.todeschini.lixeiraservicedomain.lixeira.api;

public interface RemoverLixeira {

    <T> void remover(T entity) throws IllegalAccessException;
}
