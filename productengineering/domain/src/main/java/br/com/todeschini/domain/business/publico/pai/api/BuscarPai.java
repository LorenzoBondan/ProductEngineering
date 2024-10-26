package br.com.todeschini.domain.business.publico.pai.api;

import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.business.publico.pai.DPai;

public interface BuscarPai {

    DPai buscar (Integer id);
    Paged<DPai> buscar(PageableRequest request);
}
