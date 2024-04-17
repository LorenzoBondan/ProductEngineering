package br.com.todeschini.persistence.entities.mdp;

import br.com.todeschini.persistence.entities.baseentities.BaseMaterial;
import br.com.todeschini.persistence.entities.mdf.UsedBackSheet;
import br.com.todeschini.persistence.entities.publico.Material;
import javax.persistence.*;
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
@Table(name = "tb_sheet")
public class Sheet extends BaseMaterial {

    private Double thickness;
    private Integer faces;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @OneToMany(mappedBy = "sheet")
    private List<UsedSheet> usedSheets = new ArrayList<>();

    @OneToMany(mappedBy = "sheet")
    private List<UsedBackSheet> usedBackSheets = new ArrayList<>();
}
