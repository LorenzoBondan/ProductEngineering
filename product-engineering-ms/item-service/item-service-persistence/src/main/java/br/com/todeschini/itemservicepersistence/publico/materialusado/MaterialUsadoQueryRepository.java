package br.com.todeschini.itemservicepersistence.publico.materialusado;

import br.com.todeschini.itemservicepersistence.entities.MaterialUsado;
import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@QueryService
public interface MaterialUsadoQueryRepository extends PagingAndSortingRepository<MaterialUsado, Integer>, JpaSpecificationExecutor<MaterialUsado> {

    List<MaterialUsado> findByFilho_CdfilhoAndMaterial_Cdmaterial(Integer cdfilho, Integer cdmaterial); // usado para verificar registro duplicado
}
