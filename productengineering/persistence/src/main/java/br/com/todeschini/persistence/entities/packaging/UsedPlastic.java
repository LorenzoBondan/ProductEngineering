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
@Table(name = "tb_used_plastic")
public class UsedPlastic extends BaseUsedMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ghost_id")
    private Ghost ghost;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "plastic_id")
    private Plastic plastic;

    private Boolean upper;
    private Double additional;
    private Integer width;

    public UsedPlastic(){
        this.setMeasurementUnit("M²");
    }

    @Override
    public Double calculateNetQuantity() {
        double qtd;
        double greaterMeasure;
        if(ghost.getMeasure1() >= ghost.getMeasure2()){
            greaterMeasure = ghost.getMeasure1();
        } else{
            greaterMeasure = ghost.getMeasure2();
        }
        if(upper){
            qtd = (greaterMeasure + additional) / 1000 *  ((double) width / 1000) * plastic.getGrammage();
        } else{
            qtd = greaterMeasure / 1000 * ((double) width / 1000) * plastic.getGrammage();
        }
        this.setNetQuantity(Math.round(qtd * 1e6) / 1e6);
        return qtd;
    }

    @Override
    public void calculateGrossQuantity() {
        double netQuantity = calculateNetQuantity();
        double grossQuantity = netQuantity / (1.0 - plastic.getLostPercentage() / 100);
        this.setGrossQuantity(Math.round(grossQuantity * 1e6) / 1e6);
    }
}
