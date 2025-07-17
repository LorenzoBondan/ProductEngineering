package br.com.todeschini.lixeiraservicewebapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.todeschini"})
@EnableJpaRepositories("br.com.todeschini.lixeiraservicepersistence.*")
@EnableFeignClients(basePackages = "br.com.todeschini")
@EntityScan("br.com.todeschini.lixeiraservicepersistence.*")
public class LixeiraServiceWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LixeiraServiceWebapiApplication.class, args);
    }

}
