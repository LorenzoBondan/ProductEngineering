package br.com.todeschini.itemservicedomain.processadores;

public interface MaterialProcessadorFactory {

    MaterialProcessador getProcessador(String materialType);
}
