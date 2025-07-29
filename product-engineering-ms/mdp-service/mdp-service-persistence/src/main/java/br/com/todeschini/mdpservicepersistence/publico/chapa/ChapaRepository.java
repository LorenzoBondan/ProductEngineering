package br.com.todeschini.mdpservicepersistence.publico.chapa;

import br.com.todeschini.libauditpersistence.projections.AuditoriaProjection;
import br.com.todeschini.mdpservicepersistence.entities.Chapa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ChapaRepository extends CrudRepository<Chapa, Integer> {

    @Query(nativeQuery = true, value = """
        SELECT criadopor, criadoem, situacao FROM tb_material WHERE cdmaterial = :id
    """)
    AuditoriaProjection findAuditoriaById(@Param("id") Integer id);
}
