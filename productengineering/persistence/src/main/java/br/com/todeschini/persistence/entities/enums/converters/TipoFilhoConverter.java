package br.com.todeschini.persistence.entities.enums.converters;

import br.com.todeschini.persistence.entities.enums.TipoFilho;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TipoFilhoConverter implements AttributeConverter<TipoFilho, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoFilho attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public TipoFilho convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoFilho.fromValue(dbData);
    }
}

