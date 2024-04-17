package br.com.todeschini.persistence.entities.mdf;

import br.com.todeschini.persistence.entities.baseentities.BaseMaterial;
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
}
