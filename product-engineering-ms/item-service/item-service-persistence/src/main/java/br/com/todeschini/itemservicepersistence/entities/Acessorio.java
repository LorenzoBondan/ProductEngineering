package br.com.todeschini.itemservicepersistence.entities;

import br.com.todeschini.libauditpersistence.entities.AuditoriaInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "cdacessorio", callSuper = false)
@Entity
@Table(name = "tb_acessorio")
public class Acessorio extends AuditoriaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdacessorio;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "cdmedidas")
    private Medidas medidas;

    @ManyToOne
    @JoinColumn(name = "cdcor")
    private Cor cor;

    private LocalDate implantacao;
    private Double valor;

    @OneToMany(mappedBy = "acessorio", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<AcessorioUsado> acessoriosUsados = new ArrayList<>();

    public Acessorio(Integer cdacessorio) {
        this.cdacessorio = cdacessorio;
    }
}
