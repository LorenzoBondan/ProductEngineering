package br.com.todeschini.itemservicepersistence.publico.acessoriousado;

import br.com.todeschini.itemservicepersistence.entities.AcessorioUsado;
import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@QueryService
public interface AcessorioUsadoQueryRepository extends PagingAndSortingRepository<AcessorioUsado, Integer>, JpaSpecificationExecutor<AcessorioUsado> {

    List<AcessorioUsado> findByAcessorio_CdacessorioAndFilho_Cdfilho(Integer cdacessorio, Integer cdfilho); // usado para verificar registro duplicado
}
