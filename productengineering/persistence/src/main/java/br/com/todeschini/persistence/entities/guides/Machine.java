package br.com.todeschini.persistence.entities.guides;

import br.com.todeschini.persistence.entities.audit.AuditInfo;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "tb_machine")
public class Machine extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String[] formula;

    @ManyToOne
    @JoinColumn(name = "machine_group_id")
    private MachineGroup machineGroup;

    @OneToMany(mappedBy = "machine")
    private Set<GuideMachine> guideMachines = new HashSet<>();
}
