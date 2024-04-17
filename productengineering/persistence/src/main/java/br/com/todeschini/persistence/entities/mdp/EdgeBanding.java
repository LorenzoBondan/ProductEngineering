package br.com.todeschini.persistence.entities.mdp;

import br.com.todeschini.persistence.entities.baseentities.BaseMaterial;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_edge_banding")
public class EdgeBanding extends BaseMaterial {

    private Integer height;
    private Integer thickness;

    @OneToMany(mappedBy = "edgeBanding")
    private List<UsedEdgeBanding> usedEdgeBandings = new ArrayList<>();
}
