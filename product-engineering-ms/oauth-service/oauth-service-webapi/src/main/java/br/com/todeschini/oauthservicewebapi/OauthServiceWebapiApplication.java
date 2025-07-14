package br.com.todeschini.oauthservicewebapi;

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
@EnableJpaRepositories("br.com.todeschini.oauthservicepersistence.*")
@EnableFeignClients(basePackages = "br.com.todeschini")
@EntityScan("br.com.todeschini.oauthservicepersistence.*")
@OpenAPIDefinition(info = @Info(title = "Oauth API", version = "1"))
public class OauthServiceWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthServiceWebapiApplication.class, args);
    }

}
