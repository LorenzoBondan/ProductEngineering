package br.com.todeschini.persistence.entities.mdf;

import br.com.todeschini.persistence.entities.baseentities.BaseMaterial;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_polyester")
public class Polyester extends BaseMaterial {

    @OneToMany(mappedBy = "polyester")
    private List<UsedPolyester> usedPolyesters = new ArrayList<>();
}
