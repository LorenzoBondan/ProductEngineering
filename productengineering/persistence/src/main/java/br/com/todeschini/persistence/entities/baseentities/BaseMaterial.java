package br.com.todeschini.persistence.entities.baseentities;

import br.com.todeschini.persistence.entities.audit.AuditInfo;
import br.com.todeschini.persistence.entities.publico.Color;

import javax.persistence.*;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "code", callSuper = false)
@MappedSuperclass
public class BaseMaterial extends AuditInfo {

    @Id
    private Long code;
    private String description;
    private String family;
    private LocalDate implementation;
    private Double lostPercentage;
    @Column(name = "value")
    private Double value;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;
}
