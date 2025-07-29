package br.com.todeschini.mdpservicedomain.cola;

import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.DuplicatedResourceException;
import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import br.com.todeschini.mdpservicedomain.cola.api.ColaService;
import br.com.todeschini.mdpservicedomain.cola.spi.CrudCola;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class ColaServiceImpl implements ColaService {

    private final CrudCola crudCola;

    @Override
    public Paged<DCola> buscar(PageableRequest request) {
        return crudCola.buscar(request);
    }

    @Override
    public DCola buscar(Integer id) {
        return crudCola.buscar(id);
    }

    @Override
    public DCola incluir(DCola domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudCola.incluir(domain);
    }

    @Override
    public DCola atualizar(DCola domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudCola.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {
        crudCola.inativar(id);
    }

    @Override
    public void excluir(Integer id) throws IllegalAccessException {
        crudCola.excluir(id);
    }

    private void validarRegistroDuplicado(DCola domain){
        Collection<DCola> registrosExistentes = crudCola.pesquisarPorDescricao(domain.getDescricao());

        for (DCola existente : registrosExistentes) {
            if (!existente.getCodigo().equals(Optional.ofNullable(domain.getCodigo()).orElse(-1))) {
                if (DSituacaoEnum.ATIVO.equals(existente.getSituacao())) {
                    throw new DuplicatedResourceException("Verifique o campo descrição.");
                } else if (DSituacaoEnum.INATIVO.equals(existente.getSituacao())){
                    throw new DuplicatedResourceException("Já existe um registro inativo com essa descrição. Reative-o antes de criar um novo.");
                } else if (DSituacaoEnum.LIXEIRA.equals(existente.getSituacao())){
                    throw new DuplicatedResourceException("Já existe um registro com essa descrição na lixeira. Reative-o antes de criar um novo.");
                }
            }
        }
    }
}
