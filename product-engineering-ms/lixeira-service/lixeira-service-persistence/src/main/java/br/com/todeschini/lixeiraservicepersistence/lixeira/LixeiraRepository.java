package br.com.todeschini.lixeiraservicepersistence.lixeira;

import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import br.com.todeschini.lixeiraservicepersistence.entities.Lixeira;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Map;

@QueryService
public interface LixeiraRepository extends CrudRepository<Lixeira, Integer>, JpaSpecificationExecutor<Lixeira> {

    Lixeira findByEntidadeid(Map<String, Object> entidadeId);
}
