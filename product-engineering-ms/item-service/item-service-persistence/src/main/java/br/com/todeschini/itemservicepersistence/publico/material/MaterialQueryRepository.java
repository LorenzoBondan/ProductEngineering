package br.com.todeschini.itemservicepersistence.publico.material;

import br.com.todeschini.itemservicepersistence.entities.Material;
import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

@QueryService
public interface MaterialQueryRepository extends PagingAndSortingRepository<Material, Integer>, JpaSpecificationExecutor<Material> {

    Collection<Material> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado
}
