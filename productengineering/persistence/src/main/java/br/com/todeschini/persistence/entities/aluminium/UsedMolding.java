package br.com.todeschini.persistence.entities.aluminium;

import br.com.todeschini.persistence.entities.baseentities.BaseUsedAluminiumMaterial;
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
@Table(name = "tb_used_molding")
public class UsedMolding extends BaseUsedAluminiumMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "molding_id")
    private Molding molding;

    public UsedMolding(){
        this.setMeasurementUnit("M");
    }

    @Override
    public Double calculateQuantity(){
        double quantity = (((double) this.getAluminiumSon().getMeasure1() / 1000) * 2
                + ((double) this.getAluminiumSon().getMeasure2() / 1000) * 2)
                - this.getAluminiumSon().getAluminiumType().getLessQuantity();
        this.setQuantity(Math.round(quantity * 1e6) / 1e6);
        return quantity;
    }
}
