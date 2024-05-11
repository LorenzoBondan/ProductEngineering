package br.com.todeschini.persistence.entities.mdf;

import br.com.todeschini.persistence.entities.baseentities.BaseMaterial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_back")
public class Back extends BaseMaterial {

    private Integer suffix;
    private Double thickness;
    private Integer measure1;
    private Integer measure2;

    @OneToMany(mappedBy = "back", cascade = CascadeType.ALL)
    private List<PaintingSon> sons = new ArrayList<>();

    @OneToMany(mappedBy = "back", fetch = FetchType.EAGER)
    private List<UsedBackSheet> sheets = new ArrayList<>();

    public Double calculateValue(){
        double value = 0.0;
        for(UsedBackSheet usedBackSheet : this.getSheets()){
            value += usedBackSheet.calculateValue();
        }
        value = Math.round(value * 1e2) / 1e2;
        this.setValue(value);
        return value;
    }
}
