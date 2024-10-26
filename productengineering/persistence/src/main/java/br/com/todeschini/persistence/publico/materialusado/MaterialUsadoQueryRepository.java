package br.com.todeschini.persistence.publico.materialusado;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.MaterialUsado;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface MaterialUsadoQueryRepository extends PagingAndSortingRepository<MaterialUsado, Integer>, JpaSpecificationExecutor<MaterialUsado> {

    Collection<MaterialUsado> findByFilho_CdfilhoAndMaterial_Cdmaterial(Integer cdfilho, Integer cdmaterial); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_material_usado x WHERE x.situacao = 'ATIVO' OR (:id IS NOT NULL AND x.cdmaterial_usado = :id)")
    List<MaterialUsado> findAllActiveAndCurrentOne(@Param("id") Integer id);
}
