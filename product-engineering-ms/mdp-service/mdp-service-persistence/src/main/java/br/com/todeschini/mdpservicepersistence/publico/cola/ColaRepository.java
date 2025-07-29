package br.com.todeschini.mdpservicepersistence.publico.cola;

import br.com.todeschini.libauditpersistence.projections.AuditoriaProjection;
import br.com.todeschini.mdpservicepersistence.entities.Cola;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ColaRepository extends CrudRepository<Cola, Integer> {

    @Query(nativeQuery = true, value = """
        SELECT criadopor, criadoem, situacao FROM tb_material WHERE cdmaterial = :id
    """)
    AuditoriaProjection findAuditoriaById(@Param("id") Integer id);
}
