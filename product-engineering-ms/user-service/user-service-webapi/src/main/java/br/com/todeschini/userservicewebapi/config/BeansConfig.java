package br.com.todeschini.userservicewebapi.config;

import br.com.todeschini.libauthservicedomain.auth.AuthServiceImpl;
import br.com.todeschini.libauthservicedomain.auth.api.AuthService;
import br.com.todeschini.libauthservicedomain.auth.spi.AuthMethods;
import br.com.todeschini.userservicedomain.role.RoleServiceImpl;
import br.com.todeschini.userservicedomain.role.api.RoleService;
import br.com.todeschini.userservicedomain.role.spi.CrudRole;
import br.com.todeschini.userservicedomain.user.UserServiceImpl;
import br.com.todeschini.userservicedomain.user.api.UserService;
import br.com.todeschini.userservicedomain.user.spi.CrudUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public UserService userService(CrudUser crudUser, AuthService authService) {
        return new UserServiceImpl(crudUser, authService);
    }

    @Bean
    public RoleService roleService(CrudRole crudRole) {
        return new RoleServiceImpl(crudRole);
    }

    @Bean
    public AuthService authService(AuthMethods authMethods) {
        return new AuthServiceImpl(authMethods);
    }
}
