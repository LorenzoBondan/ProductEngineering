package br.com.todeschini.mdpservicewebapi.config;

import br.com.todeschini.mdpservicedomain.chapa.ChapaServiceImpl;
import br.com.todeschini.mdpservicedomain.chapa.api.ChapaService;
import br.com.todeschini.mdpservicedomain.chapa.spi.CrudChapa;
import br.com.todeschini.mdpservicedomain.cola.ColaServiceImpl;
import br.com.todeschini.mdpservicedomain.cola.api.ColaService;
import br.com.todeschini.mdpservicedomain.cola.spi.CrudCola;
import br.com.todeschini.mdpservicedomain.fitaborda.FitaBordaServiceImpl;
import br.com.todeschini.mdpservicedomain.fitaborda.api.FitaBordaService;
import br.com.todeschini.mdpservicedomain.fitaborda.spi.CrudFitaBorda;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public ChapaService chapaService(CrudChapa crudChapa) {
        return new ChapaServiceImpl(crudChapa);
    }

    @Bean
    public ColaService colaService(CrudCola crudCola) {
        return new ColaServiceImpl(crudCola);
    }

    @Bean
    public FitaBordaService fitaBordaService(CrudFitaBorda crudFitaBorda) {
        return new FitaBordaServiceImpl(crudFitaBorda);
    }
}
