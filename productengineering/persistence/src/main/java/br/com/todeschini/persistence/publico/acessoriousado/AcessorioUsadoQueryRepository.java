package br.com.todeschini.persistence.publico.acessoriousado;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.Acessorio;
import br.com.todeschini.persistence.entities.publico.AcessorioUsado;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface AcessorioUsadoQueryRepository extends PagingAndSortingRepository<AcessorioUsado, Integer>, JpaSpecificationExecutor<AcessorioUsado> {

    Collection<AcessorioUsado> findByAcessorio_CdacessorioAndFilho_Cdfilho(Integer cdacessorio, Integer cdfilho); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_acessorio_usado p WHERE p.situacao = 'ATIVO' OR (:id IS NOT NULL AND p.cdacessorio_usado = :id)")
    List<AcessorioUsado> findAllActiveAndCurrentOne(@Param("id") Integer id);
}
