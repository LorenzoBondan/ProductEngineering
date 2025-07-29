package br.com.todeschini.itemservicedomain.filho;

import br.com.todeschini.itemservicedomain.filho.api.FilhoService;
import br.com.todeschini.itemservicedomain.filho.spi.CrudFilho;
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
public class FilhoServiceImpl implements FilhoService {

    private final CrudFilho crudFilho;

    @Override
    public Paged<DFilho> buscar(PageableRequest request) {
        return crudFilho.buscar(request);
    }

    @Override
    public List<DFilho> pesquisarPorDescricaoEMedidas(String descricao, Integer cdmedidas) {
        return crudFilho.pesquisarPorDescricaoEMedidas(descricao, cdmedidas);
    }

    @Override
    public DFilho buscar(Integer id) {
        return crudFilho.buscar(id);
    }

    @Override
    public DFilho incluir(DFilho domain) {
        domain.validar();
        validarRegistroDuplicado(domain);
        return crudFilho.incluir(domain);
    }

    @Override
    public DFilho atualizar(DFilho domain) {
        domain.validar();
        validarRegistroDuplicado(domain);
        return crudFilho.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {
        crudFilho.inativar(id);
    }

    @Override
    public void excluir(Integer id) throws IllegalAccessException {
        crudFilho.excluir(id);
    }

    private void validarRegistroDuplicado(DFilho domain){
        List<DFilho> registrosExistentes = crudFilho.pesquisarPorDescricaoECorEMedidas(
                domain.getDescricao(), domain.getCor().getCodigo(), domain.getMedidas().getCodigo()
        );

        for (DFilho existente : registrosExistentes) {
            if (!existente.getCodigo().equals(Optional.ofNullable(domain.getCodigo()).orElse(-1))) {
                if (DSituacaoEnum.ATIVO.equals(existente.getSituacao())) {
                    throw new UniqueConstraintViolationException("Registro duplicado para a combinação de descrição, cor e medidas.");
                } else if (DSituacaoEnum.INATIVO.equals(existente.getSituacao())){
                    throw new UniqueConstraintViolationException("Já existe um registro inativo com essa combinação de descrição, cor e medidas. Reative-o antes de criar um novo.");
                } else if (DSituacaoEnum.LIXEIRA.equals(existente.getSituacao())){
                    throw new UniqueConstraintViolationException("Já existe um registro com essa combinação de descrição, cor e medidas na lixeira. Reative-o antes de criar um novo.");
                }
            }
        }
    }
}
