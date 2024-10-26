package br.com.todeschini.persistence.processadores;

import br.com.todeschini.domain.business.publico.baguete.DBaguete;
import br.com.todeschini.domain.business.publico.bagueteusado.DBagueteUsado;
import br.com.todeschini.domain.business.publico.filho.DFilho;
import br.com.todeschini.domain.business.publico.material.DMaterial;
import br.com.todeschini.domain.business.publico.materialusado.DMaterialUsado;
import br.com.todeschini.domain.business.publico.materialusado.api.MaterialUsadoService;
import br.com.todeschini.persistence.publico.baguete.BagueteDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.baguete.BagueteQueryRepository;
import org.springframework.stereotype.Component;

@Component
public class BagueteProcessador implements MaterialProcessador {

    private final BagueteQueryRepository queryRepository;
    private final BagueteDomainToEntityAdapter adapter;
    private final MaterialUsadoService materialUsadoService;

    public BagueteProcessador(BagueteQueryRepository queryRepository, BagueteDomainToEntityAdapter adapter, MaterialUsadoService materialUsadoService) {
        this.queryRepository = queryRepository;
        this.adapter = adapter;
        this.materialUsadoService = materialUsadoService;
    }

    @Override
    public void processarMaterial(DFilho filho, DMaterial material) {
        DBaguete Baguete = adapter.toDomain(queryRepository.findById(material.getCodigo()).get());
        incluirBaguete(filho, Baguete);
    }

    private void incluirBaguete(DFilho filho, DBaguete Baguete) {
        DBagueteUsado bagueteUsado = new DBagueteUsado();
        bagueteUsado.setMaterial(Baguete);
        bagueteUsado.setFilho(filho);
        bagueteUsado.setUnidadeMedida("M²");
        bagueteUsado.calcularQuantidadeLiquida();
        bagueteUsado.calcularQuantidadeBruta();
        bagueteUsado.calcularValor();
        DMaterialUsado materialUsado = materialUsadoService.incluir(bagueteUsado);
        filho.getMateriaisUsados().add(materialUsado);
    }
}
