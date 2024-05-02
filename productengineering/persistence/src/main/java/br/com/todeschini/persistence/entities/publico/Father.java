package br.com.todeschini.persistence.entities.publico;

import br.com.todeschini.persistence.entities.aluminium.AluminiumSon;
import br.com.todeschini.persistence.entities.mdf.PaintingSon;
import br.com.todeschini.persistence.entities.mdp.MDPSon;
import br.com.todeschini.persistence.entities.mdp.UsedEdgeBanding;
import br.com.todeschini.persistence.entities.mdp.UsedGlue;
import br.com.todeschini.persistence.entities.mdp.UsedSheet;
import br.com.todeschini.persistence.entities.packaging.Ghost;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("Father")
public class Father extends Item implements Serializable {

    @OneToMany(mappedBy = "father", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Son> sons = new ArrayList<>();

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_father_attachment",
            joinColumns = @JoinColumn(name = "father_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id"))
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "ghost_id")
    private Ghost ghost;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    public Father(Long code){
        this.setCode(code);
    }

    public Father(Long code, String description, Integer measure1, Integer measure2, Integer measure3, Ghost ghost) {
        super(code, description, measure1, measure2, measure3);
        this.ghost = ghost;
    }

    public Double calculateValue(){
        Double value = 0.0;
        for(Son son : sons){
            if(son instanceof MDPSon){
                value += ((MDPSon) son).calculateValue();
            } else if (son instanceof PaintingSon) {
                value += ((PaintingSon) son).calculateValue();
            } else if (son instanceof AluminiumSon) {
                value += ((AluminiumSon) son).calculateValue();
            }
        }
        for(Attachment attachment : attachments){
            value += attachment.getValue();
        }
        if(this.ghost != null){
            value += ghost.calculateValue();
        }
        value = Math.round(value * 1e2) / 1e2;
        this.setValue(value);
        return value;
    }
}
