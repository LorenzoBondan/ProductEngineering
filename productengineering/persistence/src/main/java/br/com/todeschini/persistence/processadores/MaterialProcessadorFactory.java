package br.com.todeschini.persistence.processadores;

import br.com.todeschini.persistence.entities.enums.TipoMaterial;
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
        processadores.put(TipoMaterial.CHAPA_MDP.toString(), chapaProcessor);
        processadores.put(TipoMaterial.FITA_BORDA.toString(), fitaBordaProcessador);
        processadores.put(TipoMaterial.COLA.toString(), colaProcessador);
        processadores.put(TipoMaterial.CANTONEIRA.toString(), cantoneiraProcessador);
        processadores.put(TipoMaterial.TNT.toString(), tntProcessador);
        processadores.put(TipoMaterial.POLIETILENO.toString(), polietilenoProcessador);
        processadores.put(TipoMaterial.PLASTICO.toString(), plasticoProcessador);
        processadores.put(TipoMaterial.PINTURA.toString(), pinturaProcessador);
        processadores.put(TipoMaterial.PINTURA_DE_BORDA_DE_FUNDO.toString(), pinturaBordaFundoProcessador);
        processadores.put(TipoMaterial.POLIESTER.toString(), poliesterProcessador);
        processadores.put(TipoMaterial.CHAPA_MDF.toString(), chapaMDFProcessador);
        processadores.put(TipoMaterial.BAGUETE.toString(), bagueteProcessador);
    }

    public MaterialProcessador getProcessador(String materialType) {
        return Optional.ofNullable(processadores.get(materialType))
                .orElseThrow(() -> new IllegalArgumentException("Tipo de material desconhecido: " + materialType));
    }
}
