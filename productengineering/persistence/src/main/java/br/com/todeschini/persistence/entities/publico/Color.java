package br.com.todeschini.persistence.entities.publico;

import br.com.todeschini.persistence.entities.AuditInfo;
import br.com.todeschini.persistence.entities.mdp.EdgeBanding;
import br.com.todeschini.persistence.entities.mdp.Sheet;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "code", callSuper = false)
@Entity
@Table(name = "tb_color")
public class Color extends AuditInfo {

    @Id
    private Long code;
    private String name;

    @OneToMany(mappedBy = "color")
    private List<Father> fathers = new ArrayList<>();

    @OneToMany(mappedBy = "color")
    private List<Son> sons = new ArrayList<>();

    @OneToMany(mappedBy = "color")
    private List<Sheet> sheets = new ArrayList<>();

    @OneToMany(mappedBy = "color")
    private List<EdgeBanding> edgeBandings = new ArrayList<>();

    public Color(Long code, String name){
        this.code = code;
        this.name = name;
    }
}
