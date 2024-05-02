package br.com.todeschini.persistence.entities.guides;

import br.com.todeschini.persistence.entities.audit.AuditInfo;
import br.com.todeschini.persistence.entities.publico.Son;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "tb_guide")
public class Guide extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate implementation;
    private LocalDate finalDate;

    @OneToMany(mappedBy = "guide", fetch = FetchType.EAGER)
    private List<Son> sons = new ArrayList<>();

    @OneToMany(mappedBy = "guide", fetch = FetchType.EAGER)
    private Set<GuideMachine> guideMachines = new HashSet<>();

    public Double calculateValue() {
        double value = 0;
        for(GuideMachine guideMachine : this.guideMachines) {
            value += guideMachine.getMachine().getValue() * guideMachine.getMachineTime();
            value += guideMachine.getMachine().getValue() * guideMachine.getManTime();
        }
        return value;
    }
}
