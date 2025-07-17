package br.com.todeschini.itemservicedomain.processadores;

import br.com.todeschini.itemservicedomain.filho.DFilho;
import br.com.todeschini.itemservicedomain.material.DMaterial;

public interface MaterialProcessador {

    void processarMaterial(DFilho filho, DMaterial material);
}
