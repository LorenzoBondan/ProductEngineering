package br.com.todeschini.domain.business.publico.material.api;

import br.com.todeschini.domain.business.publico.material.DMaterial;

import java.util.List;

public interface FindAllActiveMaterialAndCurrentOne {

    List<DMaterial> findAllActiveAndCurrentOne (Long id);
}
