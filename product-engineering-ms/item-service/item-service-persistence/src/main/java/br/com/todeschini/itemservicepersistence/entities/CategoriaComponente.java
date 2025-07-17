package br.com.todeschini.itemservicepersistence.entities;

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
@EqualsAndHashCode(of = "cdcategoriaComponente", callSuper = false)
@Entity
@Table(name = "tb_categoria_componente")
public class CategoriaComponente extends AuditoriaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdcategoriaComponente;
    private String descricao;

    @OneToMany(mappedBy = "categoriaComponente", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Pai> pais = new ArrayList<>();

    public CategoriaComponente(Integer cdcategoriaComponente) {
        this.cdcategoriaComponente = cdcategoriaComponente;
    }
}
