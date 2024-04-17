package br.com.todeschini.domain.configurator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BPConfigurator {

    private List<BPConfiguratorSon> items = new ArrayList<>();
    private List<ColorConfigurator> colors = new ArrayList<>();
}
