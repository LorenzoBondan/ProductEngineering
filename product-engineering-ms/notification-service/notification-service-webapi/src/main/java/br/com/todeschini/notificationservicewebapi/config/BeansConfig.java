package br.com.todeschini.notificationservicewebapi.config;

import br.com.todeschini.libauthservicedomain.auth.AuthServiceImpl;
import br.com.todeschini.libauthservicedomain.auth.api.AuthService;
import br.com.todeschini.libauthservicedomain.auth.spi.AuthMethods;
import br.com.todeschini.notificationservicedomain.notification.NotificationServiceImpl;
import br.com.todeschini.notificationservicedomain.notification.api.NotificationService;
import br.com.todeschini.notificationservicedomain.notification.spi.CrudNotification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public NotificationService notificationService(CrudNotification crudNotification, AuthService authService) {
        return new NotificationServiceImpl(crudNotification, authService);
    }

    @Bean
    public AuthService authService(AuthMethods authMethods) {
        return new AuthServiceImpl(authMethods);
    }
}
