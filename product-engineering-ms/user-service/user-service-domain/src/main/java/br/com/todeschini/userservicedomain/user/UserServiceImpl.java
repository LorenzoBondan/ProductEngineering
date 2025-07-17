package br.com.todeschini.userservicedomain.user;

import br.com.todeschini.libauthservicedomain.auth.api.AuthService;
import br.com.todeschini.libexceptionhandler.exceptions.DuplicatedResourceException;
import br.com.todeschini.libexceptionhandler.exceptions.ResourceNotFoundException;
import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import br.com.todeschini.userservicedomain.user.api.UserService;
import br.com.todeschini.userservicedomain.user.spi.CrudUser;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static br.com.todeschini.userservicedomain.user.adapter.DUserConverter.convertToDUser;

@DomainService
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final CrudUser crudUser;
    private final AuthService authService;

    @Override
    public DUser buscar(Integer id) {
        authService.validateSelfOrAdmin(id);
        return crudUser.buscar(id);
    }

    @Override
    public DUser find(String email) {
        DUser user = crudUser.findByEmail(email).stream().findFirst().orElse(null);
        if (user != null) {
            authService.validateSelfOrAdmin(user.getId());
            return user;
        }
        throw new ResourceNotFoundException("Email not found: " + email);
    }

    @Override
    public Paged<DUser> buscar(PageableRequest request) {
        return crudUser.buscar(request);
    }

    @Override
    public DUser findMe() {
        return convertToDUser(authService.authenticated());
    }

    @Override
    public DUser incluir(DUser domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUser.incluir(domain);
    }

    @Override
    public DUser atualizar(DUser domain) {
        authService.validateSelfOrAdmin(domain.getId());
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUser.atualizar(domain);
    }

    @Override
    public void inativar(Integer id) {

    }

    @Override
    public void updatePassword(String newPassword, String oldPassword) {
        DUser user = convertToDUser(authService.authenticated());
        authService.validateSelfOrAdmin(user.getId());
        crudUser.updatePassword(newPassword, oldPassword, user);
    }

    @Override
    public void excluir(Integer id) {
        DUser user = convertToDUser(authService.authenticated());
        authService.validateSelfOrAdmin(user.getId());
        crudUser.excluir(id);
    }

    private void validateDuplicatedResource(DUser domain){
        if(crudUser.findByEmail(domain.getEmail())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1)))){
            throw new DuplicatedResourceException("Verify email field.");
        }
    }
}
