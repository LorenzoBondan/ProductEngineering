package br.com.todeschini.persistence.entities.enums.converters;

import br.com.todeschini.persistence.entities.enums.TipoMaterial;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TipoMaterialConverter implements AttributeConverter<TipoMaterial, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoMaterial attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public TipoMaterial convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoMaterial.fromValue(dbData);
    }
}

