package br.com.todeschini.mdpservicepersistence.processadores;

import br.com.todeschini.itemservicedomain.filho.DFilho;
import br.com.todeschini.itemservicedomain.material.DMaterial;
import br.com.todeschini.itemservicedomain.materialusado.DMaterialUsado;
import br.com.todeschini.itemservicedomain.materialusado.api.MaterialUsadoService;
import br.com.todeschini.itemservicedomain.processadores.ColaProcessador;
import br.com.todeschini.mdpservicedomain.cola.DCola;
import br.com.todeschini.mdpservicedomain.colausada.DColaUsada;
import br.com.todeschini.mdpservicepersistence.publico.cola.ColaDomainToEntityAdapter;
import br.com.todeschini.mdpservicepersistence.publico.cola.ColaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ColaProcessadorImpl implements ColaProcessador {

    private final ColaRepository repository;
    private final ColaDomainToEntityAdapter adapter;
    private final MaterialUsadoService materialUsadoService;

    @Override
    public void processarMaterial(DFilho filho, DMaterial material) {
        DCola cola = adapter.toDomain(repository.findById(material.getCodigo()).get());
        incluirCola(filho, cola);
    }

    private void incluirCola(DFilho filho, DCola Cola) {
        DColaUsada colaUsada = new DColaUsada();
        colaUsada.setMaterial(Cola);
        colaUsada.setFilho(filho);
        colaUsada.setUnidadeMedida("KG");
        colaUsada.calcularQuantidadeLiquida();
        colaUsada.calcularQuantidadeBruta();
        colaUsada.calcularValor();
        DMaterialUsado materialUsado = materialUsadoService.incluir(colaUsada);
        filho.getMateriaisUsados().add(materialUsado);
    }
}
