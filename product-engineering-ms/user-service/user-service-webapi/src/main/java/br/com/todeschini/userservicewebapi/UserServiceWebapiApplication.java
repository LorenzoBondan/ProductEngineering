package br.com.todeschini.userservicewebapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.todeschini"})
@EnableJpaRepositories("br.com.todeschini.userservicepersistence.*")
@EnableFeignClients(basePackages = "br.com.todeschini")
@EntityScan({"br.com.todeschini.userservicepersistence.*", "br.com.todeschini.libauditpersistence.entities.*"})
@OpenAPIDefinition(info = @Info(title = "User API", version = "1"))
public class UserServiceWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceWebapiApplication.class, args);
    }

}
