package br.com.todeschini.domain.business.auth.user;

import br.com.todeschini.domain.business.auth.user.api.UserService;
import br.com.todeschini.domain.business.auth.user.spi.CrudUser;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
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
    public List<DUser> findAllActiveAndCurrentOne(Long id) {
        return crudUser.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUser find(Long id) {
        return crudUser.find(id);
    }

    @Override
    public DUser insert(DUser domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudUser.insert(domain);
    }

    @Override
    public DUser update(Long id, DUser domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudUser.update(id, domain);
    }

    @Override
    public void updatePassword(String newPassword, String oldPassword) {
        crudUser.updatePassword(newPassword, oldPassword);
    }

    @Override
    public void inactivate(Long id) {
        crudUser.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUser.delete(id);
    }

    private void validateDuplicatedResource(DUser domain){
        if(crudUser.findByEmail(domain.getEmail())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo email.");
        }
    }
}
