package br.com.todeschini.persistence.entities.aluminium;

import br.com.todeschini.persistence.entities.publico.Attachment;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
public class Screw extends Attachment {

    @OneToMany(mappedBy = "screw")
    private List<UsedScrew> usedScrews = new ArrayList<>();
}
