package br.com.todeschini.mdpservicepersistence.processadores;

import br.com.todeschini.itemservicedomain.enums.DTipoMaterialEnum;
import br.com.todeschini.itemservicedomain.filho.DFilho;
import br.com.todeschini.itemservicedomain.material.DMaterial;
import br.com.todeschini.itemservicedomain.materialusado.DMaterialUsado;
import br.com.todeschini.itemservicedomain.materialusado.api.MaterialUsadoService;
import br.com.todeschini.itemservicedomain.processadores.ChapaMDFProcessador;
import br.com.todeschini.mdpservicedomain.chapa.DChapa;
import br.com.todeschini.mdpservicedomain.chapausada.DChapaUsada;
import br.com.todeschini.mdpservicepersistence.publico.chapa.ChapaDomainToEntityAdapter;
import br.com.todeschini.mdpservicepersistence.publico.chapa.ChapaQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChapaMDFProcessadorImpl implements ChapaMDFProcessador {

    private final ChapaQueryRepository queryRepository;
    private final ChapaDomainToEntityAdapter adapter;
    private final MaterialUsadoService materialUsadoService;

    @Override
    public void processarMaterial(DFilho filho, DMaterial material) {
        DChapa chapa = adapter.toDomain(queryRepository.findByEspessuraAndFacesAndCorAndTipoMaterial(
                filho.getMedidas().getEspessura(), filho.getPai().getFaces(), filho.getCor().getCodigo(), DTipoMaterialEnum.CHAPA_MDF.getValue()));
        incluirChapa(filho, chapa);
    }

    private void incluirChapa(DFilho filho, DChapa chapa) {
        DChapaUsada chapaUsada = new DChapaUsada();
        chapaUsada.setMaterial(chapa);
        chapaUsada.setFilho(filho);
        chapaUsada.setUnidadeMedida("M²");
        chapaUsada.calcularQuantidadeLiquida();
        chapaUsada.calcularQuantidadeBruta();
        chapaUsada.calcularValor();
        DMaterialUsado materialUsado = materialUsadoService.incluir(chapaUsada);
        filho.getMateriaisUsados().add(materialUsado);
    }
}
