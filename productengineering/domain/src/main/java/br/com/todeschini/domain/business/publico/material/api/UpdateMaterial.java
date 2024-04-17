package br.com.todeschini.domain.business.publico.material.api;

import br.com.todeschini.domain.business.publico.material.DMaterial;

public interface UpdateMaterial {

    DMaterial update (Long id, DMaterial material);
}
