package br.com.todeschini.persistence.entities.aluminium;

import br.com.todeschini.persistence.entities.publico.Attachment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class DrawerPull extends Attachment {

    @OneToMany(mappedBy = "drawerPull")
    private List<UsedDrawerPull> usedDrawerPulls = new ArrayList<>();
}
