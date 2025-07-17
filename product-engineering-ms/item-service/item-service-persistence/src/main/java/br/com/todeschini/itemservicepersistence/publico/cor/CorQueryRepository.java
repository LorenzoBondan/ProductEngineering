package br.com.todeschini.itemservicepersistence.publico.cor;

import br.com.todeschini.itemservicepersistence.entities.Cor;
import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

@QueryService
public interface CorQueryRepository extends PagingAndSortingRepository<Cor, Integer>, JpaSpecificationExecutor<Cor> {

    Collection<Cor> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado
}
