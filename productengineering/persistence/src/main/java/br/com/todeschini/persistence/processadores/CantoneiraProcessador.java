package br.com.todeschini.persistence.processadores;

import br.com.todeschini.domain.business.publico.cantoneira.DCantoneira;
import br.com.todeschini.domain.business.publico.cantoneirausada.DCantoneiraUsada;
import br.com.todeschini.domain.business.publico.filho.DFilho;
import br.com.todeschini.domain.business.publico.material.DMaterial;
import br.com.todeschini.domain.business.publico.materialusado.DMaterialUsado;
import br.com.todeschini.domain.business.publico.materialusado.api.MaterialUsadoService;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.publico.cantoneira.CantoneiraDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.cantoneira.CantoneiraQueryRepository;
import org.springframework.stereotype.Component;

@Component
public class CantoneiraProcessador implements MaterialProcessador {

    private final CantoneiraQueryRepository queryRepository;
    private final CantoneiraDomainToEntityAdapter adapter;
    private final MaterialUsadoService materialUsadoService;

    public CantoneiraProcessador(CantoneiraQueryRepository queryRepository, CantoneiraDomainToEntityAdapter adapter, MaterialUsadoService materialUsadoService) {
        this.queryRepository = queryRepository;
        this.adapter = adapter;
        this.materialUsadoService = materialUsadoService;
    }

    @Override
    public void processarMaterial(DFilho filho, DMaterial material) {
        DCantoneira cantoneira = adapter.toDomain(queryRepository.findById(material.getCodigo()).orElseThrow(
                () -> new ResourceNotFoundException("Cantoneira não encontrada: " + material.getCodigo())));
        incluirCantoneira(filho, cantoneira);
    }

    private void incluirCantoneira(DFilho filho, DCantoneira cantoneira) {
        DCantoneiraUsada cantoneiraUsada = new DCantoneiraUsada();
        cantoneiraUsada.setMaterial(cantoneira);
        cantoneiraUsada.setFilho(filho);
        cantoneiraUsada.setUnidadeMedida("UN");
        cantoneiraUsada.calcularQuantidadeLiquida();
        cantoneiraUsada.calcularQuantidadeBruta();
        cantoneiraUsada.calcularValor();
        DMaterialUsado materialUsado = materialUsadoService.incluir(cantoneiraUsada);
        filho.getMateriaisUsados().add(materialUsado);
    }
}
