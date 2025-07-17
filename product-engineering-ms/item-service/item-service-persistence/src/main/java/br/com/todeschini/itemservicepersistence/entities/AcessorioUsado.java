package br.com.todeschini.itemservicepersistence.entities;

import br.com.todeschini.libauditpersistence.entities.AuditoriaInfo;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "cdacessorioUsado", callSuper = false)
@Entity
@Table(name = "tb_acessorio_usado")
public class AcessorioUsado extends AuditoriaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdacessorioUsado;

    @ManyToOne
    @JoinColumn(name = "cdacessorio")
    private Acessorio acessorio;

    @ManyToOne
    @JoinColumn(name = "cdfilho")
    private Filho filho;

    private Integer quantidade;
    private Double valor;
    private String unidadeMedida = "UN";

    public AcessorioUsado(Integer cdacessorioUsado) {
        this.cdacessorioUsado = cdacessorioUsado;
    }
}
