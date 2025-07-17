package br.com.todeschini.itemservicedomain.materialusado;

import br.com.todeschini.itemservicedomain.materialusado.api.MaterialUsadoService;
import br.com.todeschini.itemservicedomain.materialusado.spi.CrudMaterialUsado;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class MaterialUsadoServiceImpl implements MaterialUsadoService {

    private final CrudMaterialUsado crudMaterialUsado;

    @Override
    public Paged<DMaterialUsado> buscar(PageableRequest request) {
        return crudMaterialUsado.buscar(request);
    }

    @Override
    public DMaterialUsado buscar(Integer id) {
        return crudMaterialUsado.buscar(id);
    }

    @Override
    public DMaterialUsado incluir(DMaterialUsado domain) {
        domain.validar();
        validarRegistroDuplicado(domain);
        return crudMaterialUsado.incluir(domain);
    }

    @Override
    public DMaterialUsado atualizar(DMaterialUsado domain) {
        domain.validar();
        validarRegistroDuplicado(domain);
        return crudMaterialUsado.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {
        crudMaterialUsado.inativar(id);
    }

    @Override
    public void excluir(Integer id) {
        crudMaterialUsado.excluir(id);
    }

    private void validarRegistroDuplicado(DMaterialUsado domain) {
        Collection<DMaterialUsado> registrosExistentes = crudMaterialUsado.pesquisarPorFilhoEMaterial(
                domain.getFilho().getCodigo(), domain.getMaterial().getCodigo()
        );

        for (DMaterialUsado existente : registrosExistentes) {
            if (!existente.getCodigo().equals(Optional.ofNullable(domain.getCodigo()).orElse(-1))) {
                if (DSituacaoEnum.ATIVO.equals(existente.getSituacao())) {
                    throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Filho e Material.");
                } else if (DSituacaoEnum.INATIVO.equals(existente.getSituacao())){
                    throw new UniqueConstraintViolationException("Já existe um registro inativo com essa combinação de Filho e Material. Reative-o antes de criar um novo.");
                } else if (DSituacaoEnum.LIXEIRA.equals(existente.getSituacao())){
                    throw new UniqueConstraintViolationException("Já existe um registro com essa combinação de Filho e Material na lixeira. Reative-o antes de criar um novo.");
                }
            }
        }
    }
}
