package br.com.todeschini.persistence.publico.pintura;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.Pintura;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface PinturaQueryRepository extends PagingAndSortingRepository<Pintura, Integer>, JpaSpecificationExecutor<Pintura> {

    @Query(nativeQuery = true, value = """
        SELECT * FROM tb_material WHERE dtype = 'Pintura' AND tipo_pintura = :tipopintura AND cdcor = :cdcor
    """)
    Collection<Pintura> findByTipoPinturaAndCor(@Param("tipopintura") Integer tipopintura,
                                                @Param("cdcor") Integer cdcor); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_material p WHERE p.situacao = 'ATIVO' OR (:id IS NOT NULL AND p.cdmaterial = :id)")
    List<Pintura> findAllActiveAndCurrentOne(@Param("id") Integer id);
}
