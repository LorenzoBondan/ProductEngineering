package br.com.todeschini.lixeiraservicedomain.lixeira.api;

import java.util.Map;

public interface RecuperarLixeira {

    <T> void recuperar(Integer id, Boolean recuperarDependencias);
    <T> void recuperarPorEntidadeId(Map<String, Object> entidadeid, Boolean recuperarDependencias);
}
