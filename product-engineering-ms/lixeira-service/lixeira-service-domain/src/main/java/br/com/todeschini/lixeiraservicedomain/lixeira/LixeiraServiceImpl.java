package br.com.todeschini.lixeiraservicedomain.lixeira;

import br.com.todeschini.lixeiraservicedomain.lixeira.api.LixeiraService;
import br.com.todeschini.lixeiraservicedomain.lixeira.spi.LixeiraOperations;
import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@DomainService
@RequiredArgsConstructor
public class LixeiraServiceImpl implements LixeiraService {

    private final LixeiraOperations lixeiraOperations;

    @Override
    public Paged<DLixeira> buscar(PageableRequest request) {
        return lixeiraOperations.buscar(request);
    }

    @Override
    public <T> void incluir(T entity) throws IllegalAccessException {
        lixeiraOperations.incluir(entity);
    }

    @Override
    public <T> void recuperar(Integer id, Boolean recuperarDependencias) {
        lixeiraOperations.recuperar(id, recuperarDependencias);
    }

    @Override
    public <T> void recuperarPorEntidadeId(Map<String, Object> entidadeid, Boolean recuperarDependencias) {
        lixeiraOperations.recuperarPorEntidadeId(entidadeid, recuperarDependencias);
    }

    @Override
    public <T> void remover(T entity) throws IllegalAccessException {
        lixeiraOperations.remover(entity);
    }
}
