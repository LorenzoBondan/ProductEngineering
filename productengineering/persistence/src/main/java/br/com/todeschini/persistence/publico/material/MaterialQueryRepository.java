package br.com.todeschini.persistence.publico.material;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.Material;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface MaterialQueryRepository extends PagingAndSortingRepository<Material, Integer>, JpaSpecificationExecutor<Material> {

    Collection<Material> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_material x WHERE x.situacao = 'ATIVO' OR (:id IS NOT NULL AND x.cdmaterial = :id)")
    List<Material> findAllActiveAndCurrentOne(@Param("id") Integer id);
}
