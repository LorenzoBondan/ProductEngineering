package br.com.todeschini.itemservicepersistence.entities;

import br.com.todeschini.itemservicepersistence.entities.enums.TipoPinturaEnum;
import br.com.todeschini.itemservicepersistence.entities.enums.converters.TipoPinturaEnumConverter;
import br.com.todeschini.libauditpersistence.entities.AuditoriaInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "cdpai", callSuper = false)
@Entity
@Table(name = "tb_pai")
public class Pai extends AuditoriaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdpai;

    @ManyToOne
    @JoinColumn(name = "cdmodelo")
    private Modelo modelo;

    @ManyToOne
    @JoinColumn(name = "cdcategoria_componente")
    private CategoriaComponente categoriaComponente;

    private String descricao;

    // fita
    private Integer bordasComprimento;
    private Integer bordasLargura;

    // embalagem
    private Integer numeroCantoneiras;
    private Boolean tntUmaFace;
    private Boolean plasticoAcima;
    private Double plasticoAdicional;
    private Integer larguraPlastico;

    // mdf
    private Integer faces;
    private Boolean especial;
    @Convert(converter = TipoPinturaEnumConverter.class)
    private TipoPinturaEnum tipoPintura;

    @OneToMany(mappedBy = "pai", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Filho> filhos = new ArrayList<>();

    public Pai(Integer cdpai) {
        this.cdpai = cdpai;
    }
}
