package br.com.todeschini.lixeiraservicedomain.lixeira.api;

public interface CriarLixeira {

    <T> void incluir(T entity) throws IllegalAccessException;
}
