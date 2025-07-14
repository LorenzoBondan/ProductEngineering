package br.com.todeschini.oauthservicewebapi.config;

import br.com.todeschini.oauthservicedomain.user.UserServiceImpl;
import br.com.todeschini.oauthservicedomain.user.api.UserService;
import br.com.todeschini.oauthservicedomain.user.spi.UserClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public UserService userService(UserClient userClient) {
        return new UserServiceImpl(userClient);
    }
}
