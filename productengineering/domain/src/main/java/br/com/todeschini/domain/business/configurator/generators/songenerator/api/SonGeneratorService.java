package br.com.todeschini.domain.business.configurator.generators.songenerator.api;

import org.springframework.stereotype.Component;

@Component
public interface SonGeneratorService extends CreateOrUpdateAluminiumSon, CreateOrUpdateMDPSon, CreateOrUpdatePaintingSon, GenerateOrUpdateSonsAndGuides {
}
