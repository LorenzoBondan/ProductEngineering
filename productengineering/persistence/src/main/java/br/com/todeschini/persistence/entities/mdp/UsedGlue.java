package br.com.todeschini.persistence.entities.mdp;

import br.com.todeschini.persistence.entities.baseentities.BaseUsedMaterial;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_used_glue")
public class UsedGlue extends BaseUsedMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "son_id")
    private MDPSon son;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "glue_id")
    private Glue glue;

    public UsedGlue() {
        this.setMeasurementUnit("KG");
    }

    public Double calculateNetQuantity(Double netEdgeBanding){
        double netQuantity = ((double) son.getMeasure3() / 1000 * netEdgeBanding * glue.getGrammage());
        this.setNetQuantity(Math.round(netQuantity * 1e6) / 1e6);
        return netQuantity;
    }

    @Override
    public Double calculateNetQuantity() {
        return null;
    }

    @Override
    public void calculateGrossQuantity(){
        double grossQuantity = this.getNetQuantity() / (1.0 - glue.getLostPercentage()/100);
        this.setGrossQuantity(Math.round(grossQuantity * 1e6) / 1e6);
    }
}
