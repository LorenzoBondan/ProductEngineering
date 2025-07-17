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
@EqualsAndHashCode(of = "cdmodelo", callSuper = false)
@Entity
@Table(name = "tb_modelo")
public class Modelo extends AuditoriaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdmodelo;
    private String descricao;

    @OneToMany(mappedBy = "modelo", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Pai> pais = new ArrayList<>();

    public Modelo(Integer cdmodelo) {
        this.cdmodelo = cdmodelo;
    }
}
