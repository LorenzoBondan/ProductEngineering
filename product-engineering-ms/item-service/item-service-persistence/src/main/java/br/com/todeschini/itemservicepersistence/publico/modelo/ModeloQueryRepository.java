package br.com.todeschini.itemservicepersistence.publico.modelo;

import br.com.todeschini.itemservicepersistence.entities.Modelo;
import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@QueryService
public interface ModeloQueryRepository extends PagingAndSortingRepository<Modelo, Integer>, JpaSpecificationExecutor<Modelo> {

    List<Modelo> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado
}
