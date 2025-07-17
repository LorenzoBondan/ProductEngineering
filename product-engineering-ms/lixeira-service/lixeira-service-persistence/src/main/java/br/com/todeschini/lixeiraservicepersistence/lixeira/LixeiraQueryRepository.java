package br.com.todeschini.lixeiraservicepersistence.lixeira;

import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import br.com.todeschini.lixeiraservicepersistence.entities.Lixeira;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

@QueryService
public interface LixeiraQueryRepository extends PagingAndSortingRepository<Lixeira, Integer>, JpaSpecificationExecutor<Lixeira> {
}
