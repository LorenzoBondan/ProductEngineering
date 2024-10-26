package br.com.todeschini.persistence.publico.roteiromaquina;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.RoteiroMaquina;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface RoteiroMaquinaQueryRepository extends PagingAndSortingRepository<RoteiroMaquina, Integer>, JpaSpecificationExecutor<RoteiroMaquina> {

    Collection<RoteiroMaquina> findByRoteiro_CdroteiroAndMaquina_Cdmaquina(Integer cdroteiro, Integer cdmaquina); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_roteiro_maquina x WHERE x.situacao = 'ATIVO' OR (:id IS NOT NULL AND x.cdroteiro_maquina = :id)")
    List<RoteiroMaquina> findAllActiveAndCurrentOne(@Param("id") Integer id);
}
