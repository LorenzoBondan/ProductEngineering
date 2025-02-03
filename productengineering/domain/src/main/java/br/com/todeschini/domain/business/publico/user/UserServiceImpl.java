package br.com.todeschini.domain.business.publico.user;

import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.Paged;
import br.com.todeschini.domain.business.auth.authservice.api.AuthService;
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
    private final AuthService authService;

    public UserServiceImpl(CrudUser crudUser, AuthService authService) {
        this.crudUser = crudUser;
        this.authService = authService;
    }

    @Override
    public List<DHistory<DUser>> buscarHistorico(Integer id) {
        return crudUser.buscarHistorico(id);
    }

    @Override
    public DUser buscar(Integer id) {
        authService.validateSelfOrAdmin(id);
        return crudUser.buscar(id);
    }

    @Override
    public DUser buscar(String email) {
        DUser user = crudUser.findByEmail(email).stream().findFirst().orElse(null);
        if(user != null) {
            authService.validateSelfOrAdmin(user.getId());
            return user;
        }
        return null;
    }

    @Override
    public DUser findMe() {
        return authService.authenticated();
    }

    @Override
    public Paged<DUser> buscar(PageableRequest request) {
        return crudUser.buscarTodos(request);
    }

    @Override
    public DUser incluir(DUser domain) {
        domain.validate();
        validarRegistroDuplicado(domain);
        return crudUser.inserir(domain);
    }

    @Override
    public DUser atualizar(DUser domain) {
        authService.validateSelfOrAdmin(domain.getId());
        domain.validate();
        validarRegistroDuplicado(domain);
        return crudUser.atualizar(domain);
    }

    @Override
    public void updatePassword(String newPassword, String oldPassword) {
        DUser user = authService.authenticated();
        authService.validateSelfOrAdmin(user.getId());
        crudUser.updatePassword(newPassword, oldPassword, user);
    }

    @Override
    public DUser substituirPorVersaoAntiga(Integer id, Integer versionId) {
        return null;
    }

    @Override
    public void inativar(Integer id) {
        DUser user = authService.authenticated();
        authService.validateSelfOrAdmin(user.getId());
        crudUser.inativar(id);
    }

    @Override
    public void excluir(Integer id) {
        DUser user = authService.authenticated();
        authService.validateSelfOrAdmin(user.getId());
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
