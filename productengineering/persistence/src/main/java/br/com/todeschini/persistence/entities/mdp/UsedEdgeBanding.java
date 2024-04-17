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
@Table(name = "tb_used_edge_banding")
public class UsedEdgeBanding extends BaseUsedMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "son_id")
    private MDPSon son;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "edge_banding_id")
    private EdgeBanding edgeBanding;

    private Integer edgeLength;
    private Integer edgeWidth;

    public UsedEdgeBanding(){
        this.setMeasurementUnit("M");
    }

    @Override
    public Double calculateNetQuantity() {
        double netQuantity = ((double) son.getMeasure1() / 1000) * (double) edgeLength + ((double) son.getMeasure2() / 1000) * (double) edgeWidth;
        this.setNetQuantity(Math.round(netQuantity * 1e6) / 1e6);
        return netQuantity;
    }

    @Override
    public void calculateGrossQuantity(){
        double netQuantity = calculateNetQuantity();
        double grossQuantity = netQuantity / (1.0 - edgeBanding.getLostPercentage()/100);
        this.setGrossQuantity(Math.round(grossQuantity * 1e6) / 1e6);
    }
}
