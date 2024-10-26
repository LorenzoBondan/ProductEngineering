package br.com.todeschini.persistence.publico.acessorio;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.Acessorio;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface AcessorioQueryRepository extends PagingAndSortingRepository<Acessorio, Integer>, JpaSpecificationExecutor<Acessorio> {

    Collection<Acessorio> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_acessorio p WHERE p.situacao = 'ATIVO' OR (:id IS NOT NULL AND p.cdacessorio = :id)")
    List<Acessorio> findAllActiveAndCurrentOne(@Param("id") Integer id);
}
