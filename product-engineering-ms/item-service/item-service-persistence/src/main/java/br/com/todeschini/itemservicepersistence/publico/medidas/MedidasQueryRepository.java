package br.com.todeschini.itemservicepersistence.publico.medidas;

import br.com.todeschini.itemservicepersistence.entities.Medidas;
import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@QueryService
public interface MedidasQueryRepository extends PagingAndSortingRepository<Medidas, Integer>, JpaSpecificationExecutor<Medidas> {

    List<Medidas> findByAlturaAndLarguraAndEspessura(Integer altura, Integer largura, Integer espesura); // usado para verificar registro duplicado
}
