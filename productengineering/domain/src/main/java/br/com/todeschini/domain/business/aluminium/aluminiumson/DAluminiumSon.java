package br.com.todeschini.domain.business.aluminium.aluminiumson;

import br.com.todeschini.domain.business.aluminium.aluminiumtype.DAluminiumType;
import br.com.todeschini.domain.business.aluminium.useddrawerpull.DUsedDrawerPull;
import br.com.todeschini.domain.business.aluminium.usedglass.DUsedGlass;
import br.com.todeschini.domain.business.aluminium.usedmolding.DUsedMolding;
import br.com.todeschini.domain.business.aluminium.usedscrew.DUsedScrew;
import br.com.todeschini.domain.business.aluminium.usedtrysquare.DUsedTrySquare;
import br.com.todeschini.domain.business.mdp.mdpson.DMDPSon;
import br.com.todeschini.domain.business.publico.son.DSon;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DAluminiumSon extends DSon {

    private DAluminiumType aluminiumType;
    private List<DMDPSon> sons = new ArrayList<>();
    private DUsedDrawerPull drawerPull;
    private List<DUsedTrySquare> trySquares = new ArrayList<>();
    private List<DUsedScrew> screws = new ArrayList<>();
    private List<DUsedMolding> moldings = new ArrayList<>();
    private DUsedGlass glass;

    public DAluminiumSon(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Tipo de Alumínio", new ObjetoNaoNuloValidator()), this.aluminiumType)
                .validate();
    }
}
