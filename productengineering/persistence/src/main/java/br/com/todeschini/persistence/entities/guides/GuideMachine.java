package br.com.todeschini.persistence.entities.guides;

import br.com.todeschini.persistence.entities.AuditInfo;
import br.com.todeschini.persistence.util.FormulaEvaluator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter
@Entity
@Table(name = "tb_guide_machine")
public class GuideMachine extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "guide_id")
    private Guide guide;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

    private Double machineTime;
    private Double manTime;
    private String measurementUnit = "MIN";

    public void calculateTime(Integer measure1, Integer measure2, Integer measure3){
        FormulaEvaluator formulaEvaluator = new FormulaEvaluator();
        double time = formulaEvaluator.evaluateFormula(
                List.of(machine.getFormula()),
                measure1,
                measure2,
                measure3
        );
        this.machineTime = Math.round(time * 1e3) / 1e3;
        this.manTime = machineTime;
    }
}
