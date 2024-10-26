package br.com.todeschini.persistence.publico.pai;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.Pai;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface PaiQueryRepository extends PagingAndSortingRepository<Pai, Integer>, JpaSpecificationExecutor<Pai> {

    Collection<Pai> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_pai x WHERE x.situacao = 'ATIVO' OR (:id IS NOT NULL AND x.cdpai = :id)")
    List<Pai> findAllActiveAndCurrentOne(@Param("id") Integer id);
}
