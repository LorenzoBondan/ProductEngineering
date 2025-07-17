package br.com.todeschini.lixeiraservicewebapi.config;

import br.com.todeschini.lixeiraservicedomain.lixeira.LixeiraServiceImpl;
import br.com.todeschini.lixeiraservicedomain.lixeira.api.LixeiraService;
import br.com.todeschini.lixeiraservicedomain.lixeira.spi.LixeiraOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public LixeiraService lixeiraService(LixeiraOperations lixeiraOperations) {
        return new LixeiraServiceImpl(lixeiraOperations);
    }
}
