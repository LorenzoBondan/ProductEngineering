package br.com.todeschini.itemservicepersistence.publico.acessorio;

import br.com.todeschini.itemservicepersistence.entities.Acessorio;
import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@QueryService
public interface AcessorioQueryRepository extends PagingAndSortingRepository<Acessorio, Integer>, JpaSpecificationExecutor<Acessorio> {

    List<Acessorio> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado
}
