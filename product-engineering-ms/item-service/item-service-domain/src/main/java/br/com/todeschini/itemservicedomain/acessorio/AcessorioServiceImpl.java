package br.com.todeschini.itemservicedomain.acessorio;

import br.com.todeschini.itemservicedomain.acessorio.api.AcessorioService;
import br.com.todeschini.itemservicedomain.acessorio.spi.CrudAcessorio;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.DuplicatedResourceException;
import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class AcessorioServiceImpl implements AcessorioService {

    private final CrudAcessorio crudAcessorio;

    @Override
    public Paged<DAcessorio> buscar(PageableRequest request) {
        return crudAcessorio.buscar(request);
    }

    @Override
    public DAcessorio buscar(Integer id) {
        return crudAcessorio.buscar(id);
    }

    @Override
    public DAcessorio incluir(DAcessorio domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudAcessorio.incluir(domain);
    }

    @Override
    public DAcessorio atualizar(DAcessorio domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudAcessorio.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {
        crudAcessorio.inativar(id);
    }

    @Override
    public void excluir(Integer id) throws IllegalAccessException {
        crudAcessorio.excluir(id);
    }

    private void validarRegistroDuplicado(DAcessorio domain){
        Collection<DAcessorio> registrosExistentes = crudAcessorio.pesquisarPorDescricao(domain.getDescricao());

        for (DAcessorio existente : registrosExistentes) {
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
