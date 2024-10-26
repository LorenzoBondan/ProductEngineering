package br.com.todeschini.persistence.publico.grupomaquina;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.GrupoMaquina;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface GrupoMaquinaQueryRepository extends PagingAndSortingRepository<GrupoMaquina, Integer>, JpaSpecificationExecutor<GrupoMaquina> {

    Collection<GrupoMaquina> findByNomeIgnoreCase(String nome); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_grupo_maquina x WHERE x.situacao = 'ATIVO' OR (:id IS NOT NULL AND x.cdgrupo_maquina = :id)")
    List<GrupoMaquina> findAllActiveAndCurrentOne(@Param("id") Integer id);
}
