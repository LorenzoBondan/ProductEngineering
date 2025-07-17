package br.com.todeschini.itemservicedomain.modelo;

import br.com.todeschini.itemservicedomain.modelo.api.ModeloService;
import br.com.todeschini.itemservicedomain.modelo.spi.CrudModelo;
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
public class ModeloServiceImpl implements ModeloService {

    private final CrudModelo crudModelo;

    @Override
    public Paged<DModelo> buscar(PageableRequest request) {
        return crudModelo.buscar(request);
    }

    @Override
    public DModelo buscar(Integer id) {
        return crudModelo.buscar(id);
    }

    @Override
    public DModelo incluir(DModelo domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudModelo.incluir(domain);
    }

    @Override
    public DModelo atualizar(DModelo domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudModelo.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {
        crudModelo.inativar(id);
    }

    @Override
    public void excluir(Integer id) {
        crudModelo.excluir(id);
    }

    private void validarRegistroDuplicado(DModelo domain){
        Collection<DModelo> registrosExistentes = crudModelo.pesquisarPorDescricao(domain.getDescricao());

        for (DModelo existente : registrosExistentes) {
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
