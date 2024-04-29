package br.com.todeschini.persistence.config;

import br.com.todeschini.domain.business.configurator.generators.fathergenerator.FatherGeneratorServiceImpl;
import br.com.todeschini.domain.business.configurator.generators.fathergenerator.api.FatherGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.fathergenerator.spi.FatherGeneratorMethods;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.GhostGeneratorServiceImpl;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.api.GhostGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.spi.GhostGeneratorMethods;
import br.com.todeschini.domain.business.configurator.generators.guidegenerator.GuideGeneratorServiceImpl;
import br.com.todeschini.domain.business.configurator.generators.guidegenerator.api.GuideGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.guidegenerator.spi.GuideGeneratorMethods;
import br.com.todeschini.domain.business.configurator.generators.songenerator.SonGeneratorServiceImpl;
import br.com.todeschini.domain.business.configurator.generators.songenerator.api.SonGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.songenerator.spi.SonGeneratorMethods;
import br.com.todeschini.domain.business.configurator.structs.aluminiumitem.AluminiumItemServiceImpl;
import br.com.todeschini.domain.business.configurator.structs.aluminiumitem.api.AluminiumItemService;
import br.com.todeschini.domain.business.configurator.structs.aluminiumitem.spi.AluminiumItemMethods;
import br.com.todeschini.domain.business.configurator.structs.mdfitem.MDFItemServiceImpl;
import br.com.todeschini.domain.business.configurator.structs.mdfitem.api.MDFItemService;
import br.com.todeschini.domain.business.configurator.structs.mdfitem.spi.MDFItemMethods;
import br.com.todeschini.domain.business.configurator.structs.mdpitem.MDPItemServiceImpl;
import br.com.todeschini.domain.business.configurator.structs.mdpitem.api.MDPItemService;
import br.com.todeschini.domain.business.configurator.structs.mdpitem.spi.MDPItemMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PersistenceConfig {

    @Autowired
    private AluminiumItemMethods aluminiumItemMethods;
    @Autowired
    private MDPItemMethods mdpItemMethods;
    @Autowired
    private MDFItemMethods mdfItemMethods;
    @Autowired
    private GhostGeneratorMethods ghostGeneratorMethods;
    @Autowired
    private FatherGeneratorMethods fatherGeneratorMethods;
    @Autowired
    private SonGeneratorMethods sonGeneratorMethods;
    @Autowired
    private GuideGeneratorMethods guideGeneratorMethods;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AluminiumItemService aluminiumItemService() {
        return new AluminiumItemServiceImpl(aluminiumItemMethods);
    }

    @Bean
    public MDPItemService mdpItemService() {
        return new MDPItemServiceImpl(mdpItemMethods);
    }

    @Bean
    public MDFItemService mdfItemService() {
        return new MDFItemServiceImpl(mdfItemMethods);
    }

    @Bean
    public GhostGeneratorService ghostGeneratorService() {
        return new GhostGeneratorServiceImpl(ghostGeneratorMethods);
    }

    @Bean
    public FatherGeneratorService fatherGeneratorService() {
        return new FatherGeneratorServiceImpl(fatherGeneratorMethods);
    }

    @Bean
    public SonGeneratorService sonGeneratorService() {
        return new SonGeneratorServiceImpl(sonGeneratorMethods);
    }

    @Bean
    public GuideGeneratorService guideGeneratorService() {
        return new GuideGeneratorServiceImpl(guideGeneratorMethods);
    }
}
