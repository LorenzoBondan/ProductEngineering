package br.com.todeschini.persistence.entities.aluminium;

import br.com.todeschini.persistence.entities.mdp.MDPSon;
import br.com.todeschini.persistence.entities.publico.Son;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("AluminiumSon")
public class AluminiumSon extends Son implements Serializable {

    @ManyToOne
    @JoinColumn(name = "aluminium_type_id")
    private AluminiumType aluminiumType;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_aluminium_son_son",
            joinColumns = @JoinColumn(name = "aluminium_son_id"),
            inverseJoinColumns = @JoinColumn(name = "son_id"))
    private List<MDPSon> sons = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "used_drawer_pull_id")
    private UsedDrawerPull drawerPull;

    @ManyToOne
    @JoinColumn(name = "used_glass_id")
    private UsedGlass glass;

    @OneToMany(mappedBy = "aluminiumSon", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<UsedTrySquare> trySquares = new ArrayList<>();

    @OneToMany(mappedBy = "aluminiumSon", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<UsedScrew> screws = new ArrayList<>();

    @OneToMany(mappedBy = "aluminiumSon", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<UsedMolding> moldings = new ArrayList<>();

    public AluminiumSon(Son son){
        this.setCode(son.getCode());
        this.setDescription(son.getDescription());
        this.setMeasure1(son.getMeasure1());
        this.setMeasure2(son.getMeasure2());
        this.setMeasure3(son.getMeasure3());
        this.setColor(son.getColor());
        this.setFather(son.getFather());
    }

    public Double calculateValue() {
        double value = 0;
        for(MDPSon son : sons){
            value += son.calculateValue();
        }
        if(drawerPull != null){
            value += drawerPull.getDrawerPull().getValue() * drawerPull.getQuantity();
        }
        if(glass != null){
            value += glass.getGlass().getValue() * glass.getQuantity();
        }
        for(UsedTrySquare trySquare : trySquares){
            value += trySquare.getTrySquare().getValue() * trySquare.getQuantity();
        }
        for(UsedScrew screw : screws){
            value += screw.getScrew().getValue() * screw.getQuantity();
        }
        for(UsedMolding molding : moldings){
            value += molding.getMolding().getValue() * molding.getQuantity();
        }
        value = Math.round(value * 1e2) / 1e2;
        this.setValue(value);
        return value;
    }
}
