package br.com.todeschini.itemservicedomain.material;

import br.com.todeschini.itemservicedomain.material.api.MaterialService;
import br.com.todeschini.itemservicedomain.material.spi.CrudMaterial;
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
public class MaterialServiceImpl implements MaterialService {

    private final CrudMaterial crudMaterial;

    @Override
    public Paged<DMaterial> buscar(PageableRequest request) {
        return crudMaterial.buscar(request);
    }

    @Override
    public DMaterial buscar(Integer id) {
        return crudMaterial.buscar(id);
    }

    @Override
    public DMaterial incluir(DMaterial domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudMaterial.incluir(domain);
    }

    @Override
    public DMaterial atualizar(DMaterial domain) {
        validarRegistroDuplicado(domain);
        domain.validar();
        return crudMaterial.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {
        crudMaterial.inativar(id);
    }

    @Override
    public void excluir(Integer id) {
        crudMaterial.excluir(id);
    }

    private void validarRegistroDuplicado(DMaterial domain){
        List<DMaterial> registrosExistentes = crudMaterial.pesquisarPorDescricao(domain.getDescricao());

        for (DMaterial existente : registrosExistentes) {
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
