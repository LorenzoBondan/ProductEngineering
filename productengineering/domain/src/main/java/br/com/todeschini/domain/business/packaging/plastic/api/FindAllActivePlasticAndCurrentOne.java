package br.com.todeschini.domain.business.packaging.plastic.api;

import br.com.todeschini.domain.business.packaging.plastic.DPlastic;

import java.util.List;

public interface FindAllActivePlasticAndCurrentOne {

    List<DPlastic> findAllActiveAndCurrentOne (Long id);
}
