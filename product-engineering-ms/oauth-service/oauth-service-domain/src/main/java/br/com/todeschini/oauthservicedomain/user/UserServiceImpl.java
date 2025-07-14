package br.com.todeschini.oauthservicedomain.user;

import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import br.com.todeschini.oauthservicedomain.user.api.UserService;
import br.com.todeschini.oauthservicedomain.user.spi.UserClient;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserClient userClient;

    public DUser findByEmail(String email) {
        return userClient.findByEmail(email);
    }
}
