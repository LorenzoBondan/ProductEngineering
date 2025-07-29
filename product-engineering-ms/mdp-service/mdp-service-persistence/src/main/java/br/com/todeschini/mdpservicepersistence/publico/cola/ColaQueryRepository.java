package br.com.todeschini.mdpservicepersistence.publico.cola;

import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import br.com.todeschini.mdpservicepersistence.entities.Cola;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@QueryService
public interface ColaQueryRepository extends PagingAndSortingRepository<Cola, Integer>, JpaSpecificationExecutor<Cola> {

    List<Cola> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado
}
