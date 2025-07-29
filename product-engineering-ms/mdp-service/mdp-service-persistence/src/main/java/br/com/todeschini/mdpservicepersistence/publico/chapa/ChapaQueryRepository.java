package br.com.todeschini.mdpservicepersistence.publico.chapa;

import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import br.com.todeschini.mdpservicepersistence.entities.Chapa;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@QueryService
public interface ChapaQueryRepository extends PagingAndSortingRepository<Chapa, Integer>, JpaSpecificationExecutor<Chapa> {

    List<Chapa> findByDescricaoIgnoreCase(String descricao); // usado para verificar registro duplicado

    @Query(nativeQuery = true, value = """
        SELECT * FROM tb_material c WHERE c.espessura = :espessura AND c.cdcor = :cdcor AND c.faces = :faces AND c.tipo_material = :tipomaterial
    """)
    Chapa findByEspessuraAndFacesAndCorAndTipoMaterial(@Param("espessura") Integer espessura,
                                                       @Param("faces") Integer faces,
                                                       @Param("cdcor") Integer cdcor,
                                                       @Param("tipomaterial") Integer tipomaterial);
}
