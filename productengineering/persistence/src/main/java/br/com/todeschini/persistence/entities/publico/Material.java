package br.com.todeschini.persistence.entities.publico;

import br.com.todeschini.persistence.entities.audit.AuditInfo;
import br.com.todeschini.persistence.entities.mdp.Sheet;
import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "tb_material")
public class Material extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "material")
    private List<Sheet> sheets = new ArrayList<>();
}
