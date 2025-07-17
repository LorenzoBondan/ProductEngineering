package br.com.todeschini.itemservicepersistence.publico.filho;

import br.com.todeschini.itemservicepersistence.entities.Filho;
import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

@QueryService
public interface FilhoQueryRepository extends PagingAndSortingRepository<Filho, Integer>, JpaSpecificationExecutor<Filho> {

    Collection<Filho> findByDescricaoIgnoreCaseAndCor_CdcorAndMedidas_Cdmedidas(String descricao, Integer cdcor, Integer cdmedidas); // usado para verificar registro duplicado
    Collection<Filho> findByDescricaoIgnoreCaseAndMedidas_Cdmedidas(String descricao, Integer cdmedidas); // usado para verificar registro duplicado dos fundos
}
