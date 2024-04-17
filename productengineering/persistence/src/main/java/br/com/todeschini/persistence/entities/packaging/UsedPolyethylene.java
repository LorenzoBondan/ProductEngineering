package br.com.todeschini.persistence.entities.packaging;

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
@Table(name = "tb_used_polyethylene")
public class UsedPolyethylene extends BaseUsedMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ghost_id")
    private Ghost ghost;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "polyethylene_id")
    private Polyethylene polyethylene;

    public UsedPolyethylene(){
        this.setMeasurementUnit("M");
    }

    @Override
    public Double calculateNetQuantity() {
        double qtd = (((double) ghost.getMeasure1() / 1000) + ((double) ghost.getMeasure2() / 1000)) * 2 * 0.98;
        this.setNetQuantity(Math.round(qtd * 1e6) / 1e6);
        return qtd;
    }

    @Override
    public void calculateGrossQuantity() {
        double netQuantity = calculateNetQuantity();
        double grossQuantity = netQuantity / (1.0 - polyethylene.getLostPercentage() / 100);
        this.setGrossQuantity(Math.round(grossQuantity * 1e6) / 1e6);
    }
}
