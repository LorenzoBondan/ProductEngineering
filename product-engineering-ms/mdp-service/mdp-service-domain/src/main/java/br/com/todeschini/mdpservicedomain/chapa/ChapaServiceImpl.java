package br.com.todeschini.mdpservicedomain.chapa;

import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.DuplicatedResourceException;
import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import br.com.todeschini.mdpservicedomain.chapa.api.ChapaService;
import br.com.todeschini.mdpservicedomain.chapa.spi.CrudChapa;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class ChapaServiceImpl implements ChapaService {

    private final CrudChapa crudChapa;

    @Override
    public Paged<DChapa> buscar(PageableRequest request) {
        return crudChapa.buscar(request);
    }

    @Override
    public DChapa buscar(Integer id) {
        return crudChapa.buscar(id);
    }

    @Override
    public DChapa incluir(DChapa domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudChapa.incluir(domain);
    }

    @Override
    public DChapa atualizar(DChapa domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudChapa.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {
        crudChapa.inativar(id);
    }

    @Override
    public void excluir(Integer id) {
        crudChapa.excluir(id);
    }

    private void validarRegistroDuplicado(DChapa domain){
        Collection<DChapa> registrosExistentes = crudChapa.pesquisarPorDescricao(domain.getDescricao());

        for (DChapa existente : registrosExistentes) {
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
