package br.com.todeschini.persistence.publico.chapa;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.Chapa;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface ChapaQueryRepository extends PagingAndSortingRepository<Chapa, Integer>, JpaSpecificationExecutor<Chapa> {

    Collection<Chapa> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = "SELECT * FROM tb_material p WHERE p.situacao = 'ATIVO' OR (:id IS NOT NULL AND p.cdmaterial = :id)")
    List<Chapa> findAllActiveAndCurrentOne(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
        SELECT * FROM tb_material c WHERE c.espessura = :espessura AND c.cdcor = :cdcor AND c.faces = :faces AND c.tipo_material = :tipomaterial
    """)
    Chapa findByEspessuraAndFacesAndCorAndTipoMaterial(@Param("espessura") Integer espessura,
                                                       @Param("faces") Integer faces,
                                                       @Param("cdcor") Integer cdcor,
                                                       @Param("tipomaterial") Integer tipomaterial);
}
