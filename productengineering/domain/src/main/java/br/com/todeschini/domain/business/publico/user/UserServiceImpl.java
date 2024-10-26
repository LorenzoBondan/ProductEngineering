package br.com.todeschini.domain.business.publico.user;

import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.business.publico.history.DHistory;
import br.com.todeschini.domain.business.publico.user.api.UserService;
import br.com.todeschini.domain.business.publico.user.spi.CrudUser;
import br.com.todeschini.domain.exceptions.RegistroDuplicadoException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UserServiceImpl implements UserService {

    private final CrudUser crudUser;

    public UserServiceImpl(CrudUser crudUser) {
        this.crudUser = crudUser;
    }

    @Override
    public List<DHistory<DUser>> buscarHistorico(Integer id) {
        return List.of();
    }

    @Override
    public List<DUser> buscarTodosMaisAtual(Integer id) {
        return List.of();
    }

    @Override
    public DUser buscar(Integer id) {
        return null;
    }

    @Override
    public Paged<DUser> buscar(PageableRequest request) {
        return null;
    }

    @Override
    public DUser incluir(DUser domain) {
        domain.validate();
        validarRegistroDuplicado(domain);
        return crudUser.inserir(domain);
    }

    @Override
    public DUser atualizar(DUser domain) {
        domain.validate();
        validarRegistroDuplicado(domain);
        return crudUser.atualizar(domain);
    }

    @Override
    public void updatePassword(String newPassword, String oldPassword) {
        crudUser.updatePassword(newPassword, oldPassword);
    }

    @Override
    public DUser substituirPorVersaoAntiga(Integer id, Integer versionId) {
        return null;
    }

    @Override
    public void inativar(Integer id) {
        crudUser.inativar(id);
    }

    @Override
    public void excluir(Integer id) {
        crudUser.remover(id);
    }

    private void validarRegistroDuplicado(DUser domain){
        if(crudUser.findByEmail(domain.getEmail())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1)))){
            throw new RegistroDuplicadoException("Verifique o campo email.");
        }
    }
}
