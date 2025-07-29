package br.com.todeschini.itemservicedomain.categoriacomponente;

import br.com.todeschini.itemservicedomain.categoriacomponente.api.CategoriaComponenteService;
import br.com.todeschini.itemservicedomain.categoriacomponente.spi.CrudCategoriaComponente;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.DuplicatedResourceException;
import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class CategoriaComponenteServiceImpl implements CategoriaComponenteService {

    private final CrudCategoriaComponente crudCategoriaComponente;

    @Override
    public Paged<DCategoriaComponente> buscar(PageableRequest request) {
        return crudCategoriaComponente.buscar(request);
    }

    @Override
    public DCategoriaComponente buscar(Integer id) {
        return crudCategoriaComponente.buscar(id);
    }

    @Override
    public DCategoriaComponente incluir(DCategoriaComponente domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudCategoriaComponente.incluir(domain);
    }

    @Override
    public DCategoriaComponente atualizar(DCategoriaComponente domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudCategoriaComponente.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {
        crudCategoriaComponente.inativar(id);
    }

    @Override
    public void excluir(Integer id) throws IllegalAccessException {
        crudCategoriaComponente.excluir(id);
    }

    private void validarRegistroDuplicado(DCategoriaComponente domain){
        List<DCategoriaComponente> registrosExistentes = crudCategoriaComponente.pesquisarPorDescricao(domain.getDescricao());

        for (DCategoriaComponente existente : registrosExistentes) {
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
