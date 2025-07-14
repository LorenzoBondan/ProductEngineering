package br.com.todeschini.oauthservicedomain.user.spi;

import br.com.todeschini.oauthservicedomain.user.DUser;

public interface UserClient {

    DUser findByEmail(String email);
}
