package br.com.todeschini.mdpservicepersistence.publico.fitaborda;

import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import br.com.todeschini.mdpservicepersistence.entities.FitaBorda;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@QueryService
public interface FitaBordaQueryRepository extends PagingAndSortingRepository<FitaBorda, Integer>, JpaSpecificationExecutor<FitaBorda> {

    List<FitaBorda> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado

    FitaBorda findByAlturaAndCor_Cdcor(Integer altura, Integer cdcor);
}
