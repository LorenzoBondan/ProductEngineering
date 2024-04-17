package br.com.todeschini.persistence.entities.mdf;

import br.com.todeschini.persistence.entities.baseentities.BaseUsedMaterial;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_used_polyester")
public class UsedPolyester extends BaseUsedMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "painting_son_id")
    private PaintingSon paintingSon;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "polyester_id")
    private Polyester polyester;

    public UsedPolyester(){
        this.setMeasurementUnit("M²");
    }

    public Double calculateNetQuantity() {
        double netQuantity = ((double) paintingSon.getMeasure1() / 1000) * ((double) paintingSon.getMeasure2() / 1000);
        this.setNetQuantity(Math.round(netQuantity * 1e6) / 1e6);
        return netQuantity;
    }

    public void calculateGrossQuantity() {
        double netQuantity = calculateNetQuantity();
        double grossQuantity = netQuantity / (1.0 - polyester.getLostPercentage()/100);
        this.setGrossQuantity(Math.round(grossQuantity * 1e6) / 1e6);
    }
}
