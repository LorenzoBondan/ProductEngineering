package br.com.todeschini.domain.business.mdp.mdpson;

import br.com.todeschini.domain.business.mdp.usededgebanding.DUsedEdgeBanding;
import br.com.todeschini.domain.business.mdp.usedglue.DUsedGlue;
import br.com.todeschini.domain.business.mdp.usedsheet.DUsedSheet;
import br.com.todeschini.domain.business.publico.son.DSon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DMDPSon extends DSon {

    private List<DUsedSheet> sheets = new ArrayList<>();
    private List<DUsedEdgeBanding> edgeBandings = new ArrayList<>();
    private List<DUsedGlue> glues = new ArrayList<>();

    public DMDPSon(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
    }
}
