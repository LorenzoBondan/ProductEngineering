package br.com.todeschini.domain.business.publico.useranexo;

import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.business.publico.useranexo.api.UserAnexoService;
import br.com.todeschini.domain.business.publico.useranexo.spi.CrudUserAnexo;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UserAnexoServiceImpl implements UserAnexoService {

    private final CrudUserAnexo crudUserAnexo;

    public UserAnexoServiceImpl(CrudUserAnexo crudUserAnexo) {
        this.crudUserAnexo = crudUserAnexo;
    }

    @Override
    public Paged<DUserAnexo> buscar(PageableRequest request) {
        return crudUserAnexo.buscarTodos(request);
    }

    @Override
    public DUserAnexo buscar(Integer id) {
        return crudUserAnexo.buscar(id);
    }

    @Override
    public List<DHistory<DUserAnexo>> buscarHistorico(Integer id) {
        return crudUserAnexo.buscarHistorico(id);
    }

    @Override
    public DUserAnexo incluir(DUserAnexo domain) {
        validarRegistroDuplicado(domain);
        domain.validate();
        return crudUserAnexo.inserir(domain);
    }

    @Override
    public DUserAnexo atualizar(DUserAnexo domain) {
        validarRegistroDuplicado(domain);
        domain.validate();
        return crudUserAnexo.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {
        crudUserAnexo.inativar(id);
    }

    @Override
    public void excluir(Integer id) {
        crudUserAnexo.remover(id);
    }

    private void validarRegistroDuplicado(DUserAnexo domain){
        if(crudUserAnexo.pesquisarPorUserEAnexo(domain.getUser().getId(), domain.getAnexo().getCodigo())
                .stream()
                .anyMatch(t -> !t.getCodigo().equals(Optional.ofNullable(domain.getCodigo()).orElse(-1)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Usuário e Anexo.");
        }
    }
}
