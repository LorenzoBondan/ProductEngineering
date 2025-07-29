package br.com.todeschini.mdpservicedomain.fitaborda;

import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.DuplicatedResourceException;
import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import br.com.todeschini.mdpservicedomain.fitaborda.api.FitaBordaService;
import br.com.todeschini.mdpservicedomain.fitaborda.spi.CrudFitaBorda;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class FitaBordaServiceImpl implements FitaBordaService {

    private final CrudFitaBorda crudFitaBorda;

    @Override
    public Paged<DFitaBorda> buscar(PageableRequest request) {
        return crudFitaBorda.buscar(request);
    }

    @Override
    public DFitaBorda buscar(Integer id) {
        return crudFitaBorda.buscar(id);
    }

    @Override
    public DFitaBorda incluir(DFitaBorda domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudFitaBorda.incluir(domain);
    }

    @Override
    public DFitaBorda atualizar(DFitaBorda domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudFitaBorda.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {
        crudFitaBorda.inativar(id);
    }

    @Override
    public void excluir(Integer id) throws IllegalAccessException {
        crudFitaBorda.excluir(id);
    }

    private void validarRegistroDuplicado(DFitaBorda domain){
        Collection<DFitaBorda> registrosExistentes = crudFitaBorda.pesquisarPorDescricao(domain.getDescricao());

        for (DFitaBorda existente : registrosExistentes) {
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
