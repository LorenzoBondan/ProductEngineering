package br.com.todeschini.persistence.processadores;

import br.com.todeschini.persistence.entities.enums.TipoMaterialEnum;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MaterialProcessadorFactory {

    private final Map<String, MaterialProcessador> processadores;

    public MaterialProcessadorFactory(
            ChapaMDPProcessador chapaProcessor, FitaBordaProcessador fitaBordaProcessador,
            ColaProcessador colaProcessador, CantoneiraProcessador cantoneiraProcessador,
            TntProcessador tntProcessador, PolietilenoProcessador polietilenoProcessador,
            PlasticoProcessador plasticoProcessador, PinturaProcessador pinturaProcessador,
            PinturaBordaFundoProcessador pinturaBordaFundoProcessador, PoliesterProcessador poliesterProcessador,
            ChapaMDFProcessador chapaMDFProcessador, BagueteProcessador bagueteProcessador
        ) {
        this.processadores = new HashMap<>();
        processadores.put(TipoMaterialEnum.CHAPA_MDP.toString(), chapaProcessor);
        processadores.put(TipoMaterialEnum.FITA_BORDA.toString(), fitaBordaProcessador);
        processadores.put(TipoMaterialEnum.COLA.toString(), colaProcessador);
        processadores.put(TipoMaterialEnum.CANTONEIRA.toString(), cantoneiraProcessador);
        processadores.put(TipoMaterialEnum.TNT.toString(), tntProcessador);
        processadores.put(TipoMaterialEnum.POLIETILENO.toString(), polietilenoProcessador);
        processadores.put(TipoMaterialEnum.PLASTICO.toString(), plasticoProcessador);
        processadores.put(TipoMaterialEnum.PINTURA.toString(), pinturaProcessador);
        processadores.put(TipoMaterialEnum.PINTURA_DE_BORDA_DE_FUNDO.toString(), pinturaBordaFundoProcessador);
        processadores.put(TipoMaterialEnum.POLIESTER.toString(), poliesterProcessador);
        processadores.put(TipoMaterialEnum.CHAPA_MDF.toString(), chapaMDFProcessador);
        processadores.put(TipoMaterialEnum.BAGUETE.toString(), bagueteProcessador);
    }

    public MaterialProcessador getProcessador(String materialType) {
        return Optional.ofNullable(processadores.get(materialType))
                .orElseThrow(() -> new IllegalArgumentException("Tipo de material desconhecido: " + materialType));
    }
}
