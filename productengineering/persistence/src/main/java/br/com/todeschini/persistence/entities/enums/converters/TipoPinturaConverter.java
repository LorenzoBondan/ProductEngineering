package br.com.todeschini.persistence.entities.enums.converters;

import br.com.todeschini.persistence.entities.enums.TipoPintura;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TipoPinturaConverter implements AttributeConverter<TipoPintura, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoPintura attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public TipoPintura convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoPintura.fromValue(dbData);
    }
}

