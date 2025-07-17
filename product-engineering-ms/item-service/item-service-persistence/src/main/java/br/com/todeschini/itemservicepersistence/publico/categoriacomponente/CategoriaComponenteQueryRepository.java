package br.com.todeschini.itemservicepersistence.publico.categoriacomponente;

import br.com.todeschini.itemservicepersistence.entities.CategoriaComponente;
import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

@QueryService
public interface CategoriaComponenteQueryRepository extends PagingAndSortingRepository<CategoriaComponente, Integer>, JpaSpecificationExecutor<CategoriaComponente> {

    Collection<CategoriaComponente> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado
}
