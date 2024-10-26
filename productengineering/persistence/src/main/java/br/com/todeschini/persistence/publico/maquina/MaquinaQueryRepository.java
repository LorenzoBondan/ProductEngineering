package br.com.todeschini.persistence.publico.maquina;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.Maquina;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface MaquinaQueryRepository extends PagingAndSortingRepository<Maquina, Integer>, JpaSpecificationExecutor<Maquina> {

    Collection<Maquina> findByNomeIgnoreCase(String nome); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_maquina x WHERE x.situacao = 'ATIVO' OR (:id IS NOT NULL AND x.cdmaquina = :id)")
    List<Maquina> findAllActiveAndCurrentOne(@Param("id") Integer id);
}
