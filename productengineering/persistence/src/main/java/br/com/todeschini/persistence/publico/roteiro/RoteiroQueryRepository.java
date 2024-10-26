package br.com.todeschini.persistence.publico.roteiro;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.Roteiro;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface RoteiroQueryRepository extends PagingAndSortingRepository<Roteiro, Integer>, JpaSpecificationExecutor<Roteiro> {

    Collection<Roteiro> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_roteiro x WHERE x.situacao = 'ATIVO' OR (:id IS NOT NULL AND x.cdroteiro = :id)")
    List<Roteiro> findAllActiveAndCurrentOne(@Param("id") Integer id);

    Boolean existsByDescricaoIgnoreCase(String descricao);
}
