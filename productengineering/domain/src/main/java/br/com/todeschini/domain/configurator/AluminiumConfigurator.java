package br.com.todeschini.domain.configurator;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AluminiumConfigurator {

    private List<AluminiumSonConfigurator> items = new ArrayList<>();
    private List<ColorConfigurator> colors = new ArrayList<>();
    private List<ScrewConfigurator> screws = new ArrayList<>();
}
