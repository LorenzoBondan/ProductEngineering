package br.com.todeschini.itemservicepersistence.entities;

import br.com.todeschini.itemservicepersistence.entities.enums.TipoFilhoEnum;
import br.com.todeschini.itemservicepersistence.entities.enums.converters.TipoFilhoEnumConverter;
import br.com.todeschini.libauditpersistence.entities.AuditoriaInfo;
import jakarta.persistence.*;
import lombok.*;

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
@EqualsAndHashCode(of = "cdfilho", callSuper = false)
@Entity
@Table(name = "tb_filho")
public class Filho extends AuditoriaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdfilho;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "cdpai")
    private Pai pai;

    @ManyToOne
    @JoinColumn(name = "cdcor")
    private Cor cor;

    @ManyToOne
    @JoinColumn(name = "cdmedidas")
    private Medidas medidas;

    private Integer roteiroId;

    private String unidadeMedida = "UN";
    private LocalDate implantacao;
    private Double valor;

    @Convert(converter = TipoFilhoEnumConverter.class)
    private TipoFilhoEnum tipo;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "tb_item_filho",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "filho_id"))
    private Set<Filho> filhos = new HashSet<>();

    @OneToMany(mappedBy = "filho", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<MaterialUsado> materiaisUsados = new ArrayList<>();

    @OneToMany(mappedBy = "filho", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<AcessorioUsado> acessoriosUsados = new ArrayList<>();

    public Filho(Integer cdfilho) {
        this.cdfilho = cdfilho;
    }

    public Filho(Integer cdfilho, Cor cor, Medidas medidas) {
        this.cdfilho = cdfilho;
        this.cor = cor;
        this.medidas = medidas;
    }
}
