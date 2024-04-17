package br.com.todeschini.persistence.entities.guides;

import br.com.todeschini.persistence.entities.AuditInfo;
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
@Table(name = "tb_machine_group")
public class MachineGroup extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "machineGroup")
    private List<Machine> machines = new ArrayList<>();
}
