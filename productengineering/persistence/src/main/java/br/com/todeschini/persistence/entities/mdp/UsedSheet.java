package br.com.todeschini.persistence.entities.mdp;

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
@Table(name = "tb_used_sheet")
public class UsedSheet extends BaseUsedMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "son_id")
    private MDPSon son;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sheet_id")
    private Sheet sheet;

    public UsedSheet() {
        this.setMeasurementUnit("M²");
    }

    @Override
    public Double calculateNetQuantity(){
        double netQuantity = ((double) son.getMeasure1() / 1000) * ((double) son.getMeasure2() /1000);
        this.setNetQuantity(Math.round(netQuantity * 1e6) / 1e6);
        return netQuantity;
    }

    @Override
    public void calculateGrossQuantity(){
        double netQuantity = calculateNetQuantity();
        double grossQuantity = netQuantity / (1.0 - sheet.getLostPercentage()/100);
        this.setGrossQuantity(Math.round(grossQuantity * 1e6) / 1e6);
    }
}
