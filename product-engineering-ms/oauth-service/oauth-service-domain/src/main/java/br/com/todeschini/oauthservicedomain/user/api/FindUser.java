package br.com.todeschini.oauthservicedomain.user.api;

import br.com.todeschini.oauthservicedomain.user.DUser;

public interface FindUser {

    DUser findByEmail(String email);
}
