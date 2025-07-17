package br.com.todeschini.itemservicepersistence.publico.processadores;

import br.com.todeschini.itemservicedomain.enums.DTipoMaterialEnum;
import br.com.todeschini.itemservicedomain.processadores.MaterialProcessador;
import br.com.todeschini.itemservicedomain.processadores.MaterialProcessadorFactory;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class MaterialProcessadorFactoryImpl implements MaterialProcessadorFactory {

    private final Map<DTipoMaterialEnum, MaterialProcessador> processadores = new EnumMap<>(DTipoMaterialEnum.class);

    public MaterialProcessadorFactoryImpl(List<MaterialProcessador> processadorList) {
        for (MaterialProcessador processador : processadorList) {
            DTipoMaterialEnum tipo = mapearTipoPorClasse(processador);
            if (tipo != null) {
                processadores.put(tipo, processador);
            }
        }
    }

    @Override
    public MaterialProcessador getProcessador(String materialType) {
        try {
            DTipoMaterialEnum tipoEnum = DTipoMaterialEnum.valueOf(materialType);
            MaterialProcessador processador = processadores.get(tipoEnum);
            if (processador == null) {
                throw new IllegalArgumentException("Nenhum processador registrado para o tipo: " + materialType);
            }
            return processador;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de material inválido ou não suportado: " + materialType);
        }
    }

    private DTipoMaterialEnum mapearTipoPorClasse(MaterialProcessador processador) {
        String className = processador.getClass().getSimpleName().toUpperCase();

        if (className.contains("CHAPAMDP")) return DTipoMaterialEnum.CHAPA_MDP;
        if (className.contains("CHAPAMDF")) return DTipoMaterialEnum.CHAPA_MDF;
        if (className.contains("FITABORDA")) return DTipoMaterialEnum.FITA_BORDA;
        if (className.contains("COLA")) return DTipoMaterialEnum.COLA;
        if (className.contains("CANTONEIRA")) return DTipoMaterialEnum.CANTONEIRA;
        if (className.contains("TNT")) return DTipoMaterialEnum.TNT;
        if (className.contains("POLIETILENO")) return DTipoMaterialEnum.POLIETILENO;
        if (className.contains("PLASTICO")) return DTipoMaterialEnum.PLASTICO;
        if (className.contains("PINTURADEBORDADEFUNDO")) return DTipoMaterialEnum.PINTURA_DE_BORDA_DE_FUNDO;
        if (className.contains("PINTURA")) return DTipoMaterialEnum.PINTURA;
        if (className.contains("POLIESTER")) return DTipoMaterialEnum.POLIESTER;
        if (className.contains("BAGUETE")) return DTipoMaterialEnum.BAGUETE;

        return null; // Se não mapeado, retorna null
    }
}
