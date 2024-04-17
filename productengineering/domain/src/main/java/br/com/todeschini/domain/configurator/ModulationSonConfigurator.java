package br.com.todeschini.domain.configurator;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ModulationSonConfigurator {

    private Long fatherCode;
    private String fatherDescription;
    private List<SonConfigurator> sons = new ArrayList<>();
    private List<Long> attachmentCodes = new ArrayList<>();
}
