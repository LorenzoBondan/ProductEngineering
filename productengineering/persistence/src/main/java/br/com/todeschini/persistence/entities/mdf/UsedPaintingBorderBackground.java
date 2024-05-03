package br.com.todeschini.persistence.entities.mdf;

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
@Table(name = "tb_used_painting_border_background")
public class UsedPaintingBorderBackground extends BaseUsedMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "painting_son_id")
    private PaintingSon paintingSon;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "painting_border_background_id")
    private PaintingBorderBackground paintingBorderBackground;

    public UsedPaintingBorderBackground(){
        this.setMeasurementUnit("M");
    }

    public Double calculateNetQuantity() {
        double netQuantity = ((double) paintingSon.getMeasure1() / 1000 + (double) paintingSon.getMeasure2() / 1000) * 2 * ((double) paintingSon.getMeasure3() / 1000);
        this.setNetQuantity(Math.round(netQuantity * 1e6) / 1e6);
        return netQuantity;
    }

    public void calculateGrossQuantity() {
        double netQuantity = calculateNetQuantity();
        double grossQuantity = netQuantity / (1.0 - paintingBorderBackground.getLostPercentage()/100);
        this.setGrossQuantity(Math.round(grossQuantity * 1e6) / 1e6);
    }

    @Override
    public Double calculateValue() {
        return Math.round(paintingBorderBackground.getValue() * this.getNetQuantity() * 1e2) / 1e2;
    }
}
