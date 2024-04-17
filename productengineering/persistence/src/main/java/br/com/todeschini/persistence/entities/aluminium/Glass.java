package br.com.todeschini.persistence.entities.aluminium;

import br.com.todeschini.persistence.entities.publico.Attachment;
import br.com.todeschini.persistence.entities.publico.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Glass extends Attachment {

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @OneToMany(mappedBy = "glass")
    private List<UsedGlass> usedGlasses = new ArrayList<>();
}
