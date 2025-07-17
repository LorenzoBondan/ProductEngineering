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
@EqualsAndHashCode(of = "cdcor", callSuper = false)
@Entity
@Table(name = "tb_cor")
public class Cor extends AuditoriaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdcor;
    private String descricao;
    private String hexa;

    @OneToMany(mappedBy = "cor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Filho> filhos = new ArrayList<>();

    @OneToMany(mappedBy = "cor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Material> materiais = new ArrayList<>();

    public Cor(Integer cdcor) {
        this.cdcor = cdcor;
    }
}
