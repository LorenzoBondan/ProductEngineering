package br.com.todeschini.itemservicedomain.acessoriousado;

import br.com.todeschini.itemservicedomain.acessoriousado.api.AcessorioUsadoService;
import br.com.todeschini.itemservicedomain.acessoriousado.spi.CrudAcessorioUsado;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class AcessorioUsadoServiceImpl implements AcessorioUsadoService {

    private final CrudAcessorioUsado crudAcessorioUsado;

    @Override
    public Paged<DAcessorioUsado> buscar(PageableRequest request) {
        return crudAcessorioUsado.buscar(request);
    }

    @Override
    public DAcessorioUsado buscar(Integer id) {
        return crudAcessorioUsado.buscar(id);
    }

    @Override
    public DAcessorioUsado incluir(DAcessorioUsado domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudAcessorioUsado.incluir(domain);
    }

    @Override
    public DAcessorioUsado atualizar(DAcessorioUsado domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudAcessorioUsado.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {
        crudAcessorioUsado.inativar(id);
    }

    @Override
    public void excluir(Integer id) throws IllegalAccessException {
        crudAcessorioUsado.excluir(id);
    }

    private void validarRegistroDuplicado(DAcessorioUsado domain){
        List<DAcessorioUsado> registrosExistentes = crudAcessorioUsado.pesquisarPorAcessorioEFilho(
                domain.getAcessorio().getCodigo(), domain.getFilho().getCodigo()
        );

        for (DAcessorioUsado existente : registrosExistentes) {
            if (!existente.getCodigo().equals(Optional.ofNullable(domain.getCodigo()).orElse(-1))) {
                if (DSituacaoEnum.ATIVO.equals(existente.getSituacao())) {
                    throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Filho e Acessório.");
                } else if (DSituacaoEnum.INATIVO.equals(existente.getSituacao())){
                    throw new UniqueConstraintViolationException("Já existe um registro inativo com essa combinação de Filho e Acessório. Reative-o antes de criar um novo.");
                } else if (DSituacaoEnum.LIXEIRA.equals(existente.getSituacao())){
                    throw new UniqueConstraintViolationException("Já existe um registro com essa combinação de Filho e Acessório na lixeira. Reative-o antes de criar um novo.");
                }
            }
        }
    }
}
