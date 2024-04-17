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
@Table(name = "tb_used_nonwoven_fabric")
public class UsedNonwovenFabric extends BaseUsedMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ghost_id")
    private Ghost ghost;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "nonwoven_fabric_id")
    private NonwovenFabric nonwovenFabric;
    
    private Boolean oneFace;

    public UsedNonwovenFabric(){
        this.setMeasurementUnit("M²");
    }

    @Override
    public Double calculateNetQuantity() {
        double qtd;
        double complement = (double) ghost.getMeasure3() * 2;
        if(oneFace){
            qtd = (((double) ghost.getMeasure1() + complement)/1000) * (((double) ghost.getMeasure2() + complement) / 1000);
        } else{
            qtd = (((double) ghost.getMeasure1() / 1000) * ((double) ghost.getMeasure2() / 1000) * 2)
                    + (((double) ghost.getMeasure1() / 1000) * 2 * ((double) ghost.getMeasure3() / 1000))
                    + (((double) ghost.getMeasure2() / 1000) * 2 * ((double) ghost.getMeasure3() / 1000));
        }
        this.setNetQuantity(Math.round(qtd * 1e6) / 1e6);
        return qtd;
    }

    @Override
    public void calculateGrossQuantity() {
        double netQuantity = calculateNetQuantity();
        double grossQuantity = netQuantity / (1.0 - nonwovenFabric.getLostPercentage()/100);
        this.setGrossQuantity(Math.round(grossQuantity * 1e6) / 1e6);
    }
}
