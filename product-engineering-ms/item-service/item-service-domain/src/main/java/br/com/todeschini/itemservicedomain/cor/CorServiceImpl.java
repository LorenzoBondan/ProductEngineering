package br.com.todeschini.itemservicedomain.cor;

import br.com.todeschini.itemservicedomain.cor.api.CorService;
import br.com.todeschini.itemservicedomain.cor.spi.CrudCor;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class CorServiceImpl implements CorService {

    private final CrudCor crudCor;

    @Override
    public Paged<DCor> buscar(PageableRequest request) {
        return crudCor.buscar(request);
    }

    @Override
    public DCor buscar(Integer id) {
        return crudCor.buscar(id);
    }

    @Override
    public DCor incluir(DCor domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudCor.incluir(domain);
    }

    @Override
    public DCor atualizar(DCor domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudCor.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {
        crudCor.inativar(id);
    }

    @Override
    public void excluir(Integer id) {
        crudCor.excluir(id);
    }

    private void validarRegistroDuplicado(DCor domain){
        List<DCor> registrosExistentes = crudCor.pesquisarPorDescricao(domain.getDescricao());

        for (DCor existente : registrosExistentes) {
            if (!existente.getCodigo().equals(Optional.ofNullable(domain.getCodigo()).orElse(-1))) {
                if (DSituacaoEnum.ATIVO.equals(existente.getSituacao())) {
                    throw new RegistroDuplicadoException("Verifique o campo descrição.");
                } else if (DSituacaoEnum.INATIVO.equals(existente.getSituacao())){
                    throw new RegistroDuplicadoException("Já existe um registro inativo com essa descrição. Reative-o antes de criar um novo.");
                } else if (DSituacaoEnum.LIXEIRA.equals(existente.getSituacao())){
                    throw new RegistroDuplicadoException("Já existe um registro com essa descrição na lixeira. Reative-o antes de criar um novo.");
                }
            }
        }
    }
}
