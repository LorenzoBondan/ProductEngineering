package br.com.todeschini.persistence.processadores;

import br.com.todeschini.domain.business.enums.DTipoMaterial;
import br.com.todeschini.domain.business.publico.chapa.DChapa;
import br.com.todeschini.domain.business.publico.chapausada.DChapaUsada;
import br.com.todeschini.domain.business.publico.filho.DFilho;
import br.com.todeschini.domain.business.publico.material.DMaterial;
import br.com.todeschini.domain.business.publico.materialusado.DMaterialUsado;
import br.com.todeschini.domain.business.publico.materialusado.api.MaterialUsadoService;
import br.com.todeschini.persistence.publico.chapa.ChapaDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.chapa.ChapaQueryRepository;
import org.springframework.stereotype.Component;

@Component
public class ChapaMDPProcessador implements MaterialProcessador {

    private final ChapaQueryRepository queryRepository;
    private final ChapaDomainToEntityAdapter adapter;
    private final MaterialUsadoService materialUsadoService;

    public ChapaMDPProcessador(ChapaQueryRepository queryRepository, ChapaDomainToEntityAdapter adapter, MaterialUsadoService materialUsadoService) {
        this.queryRepository = queryRepository;
        this.adapter = adapter;
        this.materialUsadoService = materialUsadoService;
    }

    @Override
    public void processarMaterial(DFilho filho, DMaterial material) {
        DChapa chapa = adapter.toDomain(queryRepository.findByEspessuraAndFacesAndCorAndTipoMaterial(
                filho.getMedidas().getEspessura(), filho.getPai().getFaces(), filho.getCor().getCodigo(), DTipoMaterial.CHAPA_MDP.getValue()));
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
