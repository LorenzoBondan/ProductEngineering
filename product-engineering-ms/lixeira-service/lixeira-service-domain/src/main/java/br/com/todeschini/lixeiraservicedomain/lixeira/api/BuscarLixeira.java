package br.com.todeschini.lixeiraservicedomain.lixeira.api;

import br.com.todeschini.lixeiraservicedomain.lixeira.DLixeira;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;

public interface BuscarLixeira {

    Paged<DLixeira> buscar(PageableRequest request);
}
