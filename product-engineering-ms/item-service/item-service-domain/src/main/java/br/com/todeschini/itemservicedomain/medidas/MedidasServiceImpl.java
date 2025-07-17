package br.com.todeschini.itemservicedomain.medidas;

import br.com.todeschini.itemservicedomain.medidas.api.MedidasService;
import br.com.todeschini.itemservicedomain.medidas.spi.CrudMedidas;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class MedidasServiceImpl implements MedidasService {

    private final CrudMedidas crudMedidas;

    @Override
    public Paged<DMedidas> buscar(PageableRequest request) {
        return crudMedidas.buscar(request);
    }

    @Override
    public DMedidas buscar(Integer id) {
        return crudMedidas.buscar(id);
    }

    @Override
    public List<DMedidas> buscarPorAlturaELarguraEEspessura(Integer altura, Integer largura, Integer espessura) {
        return crudMedidas.pesquisarPorAlturaELarguraEEspessura(altura, largura, espessura);
    }

    @Override
    public DMedidas incluir(DMedidas domain) {
        domain.validar();
        validarRegistroDuplicado(domain);
        return crudMedidas.incluir(domain);
    }

    @Override
    public DMedidas atualizar(DMedidas domain) {
        domain.validar();
        validarRegistroDuplicado(domain);
        return crudMedidas.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {
        crudMedidas.inativar(id);
    }

    @Override
    public void excluir(Integer id) {
        crudMedidas.excluir(id);
    }

    private void validarRegistroDuplicado(DMedidas domain){
        Collection<DMedidas> registrosExistentes = crudMedidas.pesquisarPorAlturaELarguraEEspessura(
                domain.getAltura(), domain.getLargura(), domain.getEspessura()
        );

        for (DMedidas existente : registrosExistentes) {
            if (!existente.getCodigo().equals(Optional.ofNullable(domain.getCodigo()).orElse(-1))) {
                if (DSituacaoEnum.ATIVO.equals(existente.getSituacao())) {
                    throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Altura, Largura e Espessura.");
                } else if (DSituacaoEnum.INATIVO.equals(existente.getSituacao())){
                    throw new UniqueConstraintViolationException("Já existe um registro inativo com essa combinação de Altura, Largura e Espessura. Reative-o antes de criar um novo.");
                } else if (DSituacaoEnum.LIXEIRA.equals(existente.getSituacao())){
                    throw new UniqueConstraintViolationException("Já existe um registro com essa combinação de de Altura, Largura e Espessura na lixeira. Reative-o antes de criar um novo.");
                }
            }
        }
    }
}
