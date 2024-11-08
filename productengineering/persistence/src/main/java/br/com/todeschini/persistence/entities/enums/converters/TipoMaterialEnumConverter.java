package br.com.todeschini.persistence.entities.enums.converters;

import br.com.todeschini.persistence.entities.enums.TipoMaterialEnum;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TipoMaterialEnumConverter implements AttributeConverter<TipoMaterialEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoMaterialEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public TipoMaterialEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoMaterialEnum.fromValue(dbData);
    }
}

