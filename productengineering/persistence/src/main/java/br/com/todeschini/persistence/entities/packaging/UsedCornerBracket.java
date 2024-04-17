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
@Table(name = "tb_used_corner_bracket")
public class UsedCornerBracket extends BaseUsedMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ghost_id")
    private Ghost ghost;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "corner_bracket_id")
    private CornerBracket cornerBracket;

    private Integer quantity;

    public UsedCornerBracket(){
        this.setMeasurementUnit("UN");
    }

    @Override
    public Double calculateNetQuantity() {
        this.setNetQuantity(Double.parseDouble(String.valueOf(quantity)));
        return this.getNetQuantity();
    }

    @Override
    public void calculateGrossQuantity() {
        this.setGrossQuantity(this.getNetQuantity());
    }
}
