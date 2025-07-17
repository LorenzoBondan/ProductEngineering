package br.com.todeschini.itemservicewebapi.config;

import br.com.todeschini.itemservicedomain.acessorio.AcessorioServiceImpl;
import br.com.todeschini.itemservicedomain.acessorio.api.AcessorioService;
import br.com.todeschini.itemservicedomain.acessorio.spi.CrudAcessorio;
import br.com.todeschini.itemservicedomain.acessoriousado.AcessorioUsadoServiceImpl;
import br.com.todeschini.itemservicedomain.acessoriousado.api.AcessorioUsadoService;
import br.com.todeschini.itemservicedomain.acessoriousado.spi.CrudAcessorioUsado;
import br.com.todeschini.itemservicedomain.categoriacomponente.CategoriaComponenteServiceImpl;
import br.com.todeschini.itemservicedomain.categoriacomponente.api.CategoriaComponenteService;
import br.com.todeschini.itemservicedomain.categoriacomponente.spi.CrudCategoriaComponente;
import br.com.todeschini.itemservicedomain.cor.CorServiceImpl;
import br.com.todeschini.itemservicedomain.cor.api.CorService;
import br.com.todeschini.itemservicedomain.cor.spi.CrudCor;
import br.com.todeschini.itemservicedomain.filho.FilhoServiceImpl;
import br.com.todeschini.itemservicedomain.filho.api.FilhoService;
import br.com.todeschini.itemservicedomain.filho.spi.CrudFilho;
import br.com.todeschini.itemservicedomain.material.MaterialServiceImpl;
import br.com.todeschini.itemservicedomain.material.api.MaterialService;
import br.com.todeschini.itemservicedomain.material.spi.CrudMaterial;
import br.com.todeschini.itemservicedomain.medidas.MedidasServiceImpl;
import br.com.todeschini.itemservicedomain.medidas.api.MedidasService;
import br.com.todeschini.itemservicedomain.medidas.spi.CrudMedidas;
import br.com.todeschini.itemservicedomain.modelo.ModeloServiceImpl;
import br.com.todeschini.itemservicedomain.modelo.api.ModeloService;
import br.com.todeschini.itemservicedomain.modelo.spi.CrudModelo;
import br.com.todeschini.itemservicedomain.pai.PaiServiceImpl;
import br.com.todeschini.itemservicedomain.pai.api.PaiService;
import br.com.todeschini.itemservicedomain.pai.spi.CrudPai;
import br.com.todeschini.itemservicedomain.processadores.MaterialProcessadorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public AcessorioService acessorioService(CrudAcessorio crudAcessorio) {
        return new AcessorioServiceImpl(crudAcessorio);
    }

    @Bean
    public AcessorioUsadoService acessorioUsadoService(CrudAcessorioUsado crudAcessorioUsado) {
        return new AcessorioUsadoServiceImpl(crudAcessorioUsado);
    }

    @Bean
    public CategoriaComponenteService categoriaComponenteService(CrudCategoriaComponente crudCategoriaComponente) {
        return new CategoriaComponenteServiceImpl(crudCategoriaComponente);
    }

    @Bean
    public CorService corService(CrudCor crudCor) {
        return new CorServiceImpl(crudCor);
    }

    @Bean
    public FilhoService filhoService(CrudFilho crudFilho) {
        return new FilhoServiceImpl(crudFilho);
    }

    @Bean
    public MaterialService materialService(CrudMaterial crudMaterial) {
        return new MaterialServiceImpl(crudMaterial);
    }

    @Bean
    public MedidasService medidasService(CrudMedidas crudMedidas) {
        return new MedidasServiceImpl(crudMedidas);
    }

    @Bean
    public ModeloService modeloService(CrudModelo crudModelo) {
        return new ModeloServiceImpl(crudModelo);
    }

    @Bean
    public PaiService paiService(CrudPai crudPai, ModeloService modeloService, CategoriaComponenteService categoriaComponenteService,
                                 FilhoService filhoService, MedidasService medidasService, MaterialService materialService,
                                 CorService corService, AcessorioService acessorioService, AcessorioUsadoService acessorioUsadoService,
                                 MaterialProcessadorFactory materialProcessadorFactory) {
        return new PaiServiceImpl(crudPai, modeloService, categoriaComponenteService, filhoService, medidasService, materialService
        , corService, acessorioService, acessorioUsadoService, materialProcessadorFactory);
    }
}
