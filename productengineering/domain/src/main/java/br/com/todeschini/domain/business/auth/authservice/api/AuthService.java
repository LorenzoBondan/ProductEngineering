package br.com.todeschini.domain.business.auth.authservice.api;

import org.springframework.stereotype.Component;

@Component
public interface AuthService extends Authenticated, ValidateSelfOrAdmin {
}
