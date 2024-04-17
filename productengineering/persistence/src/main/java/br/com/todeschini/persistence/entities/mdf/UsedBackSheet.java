package br.com.todeschini.persistence.entities.mdf;

import br.com.todeschini.persistence.entities.baseentities.BaseUsedMaterial;
import br.com.todeschini.persistence.entities.mdp.Sheet;
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
@Table(name = "tb_used_back_sheet")
public class UsedBackSheet extends BaseUsedMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "back_id")
    private Back back;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sheet_id")
    private Sheet sheet;

    public UsedBackSheet() {
        this.setMeasurementUnit("M²");
    }

    @Override
    public Double calculateNetQuantity() {
        double netQuantity = ((double) back.getMeasure1() / 1000) * ((double) back.getMeasure2() / 1000);
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
