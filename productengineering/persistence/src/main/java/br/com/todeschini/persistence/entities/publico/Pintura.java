package br.com.todeschini.persistence.entities.publico;

import br.com.todeschini.domain.metadata.Entidade;
import br.com.todeschini.persistence.entities.enums.TipoPintura;
import br.com.todeschini.persistence.entities.enums.converters.TipoPinturaConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Entidade
public class Pintura extends Material {

    @Convert(converter = TipoPinturaConverter.class)
    private TipoPintura tipoPintura;
}
