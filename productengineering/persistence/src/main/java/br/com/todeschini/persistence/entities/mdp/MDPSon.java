package br.com.todeschini.persistence.entities.mdp;

import br.com.todeschini.persistence.entities.aluminium.AluminiumSon;
import br.com.todeschini.persistence.entities.publico.Son;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("MDPSon")
public class MDPSon extends Son implements Serializable {

    @OneToMany(mappedBy = "son", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<UsedSheet> sheets = new HashSet<>();

    @OneToMany(mappedBy = "son", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<UsedEdgeBanding> edgeBandings = new HashSet<>();

    @OneToMany(mappedBy = "son", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<UsedGlue> glues = new HashSet<>();

    @ManyToMany(mappedBy = "sons", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<AluminiumSon> aluminiumSons = new HashSet<>();

    public MDPSon(Son son){
        this.setCode(son.getCode());
        this.setDescription(son.getDescription());
        this.setMeasure1(son.getMeasure1());
        this.setMeasure2(son.getMeasure2());
        this.setMeasure3(son.getMeasure3());
        this.setColor(son.getColor());
        this.setFather(son.getFather());
    }
}
