package br.com.todeschini.domain.configurator;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BPConfiguratorSon {

    private Long fatherCode;
    private String description;
    private Long sonCode;
    private List<Long> machinesIds = new ArrayList<>();
}
