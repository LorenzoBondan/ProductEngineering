package br.com.todeschini.domain.configurator;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SonConfigurator {

    private Long code;
    private String description;
    private Integer edgeLength;
    private Integer edgeWidth;
    private List<Long> machinesIds = new ArrayList<>();
}
