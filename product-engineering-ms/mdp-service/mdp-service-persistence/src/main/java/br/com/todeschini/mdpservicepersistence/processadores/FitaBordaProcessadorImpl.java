package br.com.todeschini.mdpservicepersistence.processadores;

import br.com.todeschini.itemservicedomain.filho.DFilho;
import br.com.todeschini.itemservicedomain.material.DMaterial;
import br.com.todeschini.itemservicedomain.materialusado.DMaterialUsado;
import br.com.todeschini.itemservicedomain.materialusado.api.MaterialUsadoService;
import br.com.todeschini.itemservicedomain.processadores.FitaBordaProcessador;
import br.com.todeschini.mdpservicedomain.fitaborda.DFitaBorda;
import br.com.todeschini.mdpservicedomain.fitabordausada.DFitaBordaUsada;
import br.com.todeschini.mdpservicepersistence.publico.fitaborda.FitaBordaDomainToEntityAdapter;
import br.com.todeschini.mdpservicepersistence.publico.fitaborda.FitaBordaQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FitaBordaProcessadorImpl implements FitaBordaProcessador {

    private final FitaBordaQueryRepository queryRepository;
    private final FitaBordaDomainToEntityAdapter adapter;
    private final MaterialUsadoService materialUsadoService;

    @Override
    public void processarMaterial(DFilho filho, DMaterial material) {
        DFitaBorda fitaBorda = adapter.toDomain(queryRepository.findByAlturaAndCor_Cdcor(
                filho.getMedidas().getEspessura() + 3, filho.getCor().getCodigo()));
        incluirFitaBorda(filho, fitaBorda);
    }

    private void incluirFitaBorda(DFilho filho, DFitaBorda FitaBorda) {
        DFitaBordaUsada FitaBordaUsada = new DFitaBordaUsada();
        FitaBordaUsada.setMaterial(FitaBorda);
        FitaBordaUsada.setFilho(filho);
        FitaBordaUsada.setUnidadeMedida("M");
        FitaBordaUsada.calcularQuantidadeLiquida();
        FitaBordaUsada.calcularQuantidadeBruta();
        FitaBordaUsada.calcularValor();
        DMaterialUsado materialUsado = materialUsadoService.incluir(FitaBordaUsada);
        filho.getMateriaisUsados().add(materialUsado);
    }
}
