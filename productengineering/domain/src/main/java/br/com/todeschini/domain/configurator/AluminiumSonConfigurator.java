package br.com.todeschini.domain.configurator;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AluminiumSonConfigurator {

    private Long fatherCode;
    private Long aluminiumSonCode;
    private String description;

    private List<SonConfigurator> sons = new ArrayList<>();
}
