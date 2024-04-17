package br.com.todeschini.domain.configurator;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ModulationConfigurator {

    private List<ModulationSonConfigurator> items = new ArrayList<>();
    private List<ColorConfigurator> colors = new ArrayList<>();
}
