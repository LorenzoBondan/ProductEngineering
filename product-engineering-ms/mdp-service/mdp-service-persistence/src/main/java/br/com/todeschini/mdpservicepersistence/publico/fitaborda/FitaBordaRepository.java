package br.com.todeschini.mdpservicepersistence.publico.fitaborda;

import br.com.todeschini.libauditpersistence.projections.AuditoriaProjection;
import br.com.todeschini.mdpservicepersistence.entities.FitaBorda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FitaBordaRepository extends CrudRepository<FitaBorda, Integer> {

    @Query(nativeQuery = true, value = """
        SELECT criadopor, criadoem, situacao FROM tb_material WHERE cdmaterial = :id
    """)
    AuditoriaProjection findAuditoriaById(@Param("id") Integer id);
}
