package br.com.todeschini.notificationservicewebapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients(basePackages = {"br.com.todeschini"})
@ComponentScan(value = {"br.com.todeschini", "br.com.todeschini.notificationservicedomain.*"})
@EnableJpaRepositories("br.com.todeschini.notificationservicepersistence.*")
@EntityScan("br.com.todeschini.notificationservicepersistence.*")
@SpringBootApplication
public class NotificationServiceWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceWebapiApplication.class, args);
    }

}
