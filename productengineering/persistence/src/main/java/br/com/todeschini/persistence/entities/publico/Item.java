package br.com.todeschini.persistence.entities.publico;

import br.com.todeschini.persistence.entities.AuditInfo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "code", callSuper = false)
@Entity
@Table(name = "tb_item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public class Item extends AuditInfo implements Serializable {

    @Id
    @NotNull
    private Long code;
    private String description;
    private Integer measure1;
    private Integer measure2;
    private Integer measure3;
    private String measurementUnit = "UN";

    public Item(Long code, String description, Integer measure1, Integer measure2, Integer measure3) {
        this.code = code;
        this.description = description;
        this.measure1 = measure1;
        this.measure2 = measure2;
        this.measure3 = measure3;
    }

    public void setMeasurements(){
        for (int i = 0; i < description.length(); i++) {
            String caracter = description.substring(i, i + 1);
            if (Character.isDigit(caracter.charAt(0)) && description.substring(i + 2, i + 3).equals("X")) {
                String measurementsBegin = description.substring(i - 2).trim();
                int spaceMeasurementsExit = measurementsBegin.indexOf(" ");
                String measurements = measurementsBegin.substring(0, spaceMeasurementsExit).trim();
                int firstX = measurements.indexOf("X");
                measure1 = Integer.parseInt(measurements.substring(0, firstX).trim());
                int secondX = measurements.lastIndexOf("X");
                measure2 = Integer.parseInt(measurements.substring(firstX + 1, secondX).trim());
                measure3 = Integer.parseInt(measurements.substring(secondX + 1).trim());
                break;
            }
        }
    }
}
