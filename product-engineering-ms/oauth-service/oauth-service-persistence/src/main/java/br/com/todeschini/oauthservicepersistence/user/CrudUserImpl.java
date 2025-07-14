package br.com.todeschini.oauthservicepersistence.user;

import br.com.todeschini.libexceptionhandler.exceptions.ResourceNotFoundException;
import br.com.todeschini.oauthservicedomain.user.DUser;
import br.com.todeschini.oauthservicedomain.user.spi.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrudUserImpl implements UserClient {

    private final UserRepository repository;
    private final UserDomainToEntityAdapter adapter;

    @Override
    public DUser findByEmail(String email) {
        return adapter.toDomain(repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found: " + email)));
    }
}
