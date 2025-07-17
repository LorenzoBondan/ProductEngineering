package br.com.todeschini.itemservicewebapi;

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
@EnableJpaRepositories("br.com.todeschini.itemservicepersistence.*")
@EnableFeignClients(basePackages = "br.com.todeschini")
@EntityScan("br.com.todeschini.itemservicepersistence.*")
@OpenAPIDefinition(info = @Info(title = "Item API", version = "1"))
public class ItemServiceWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemServiceWebapiApplication.class, args);
    }

}
