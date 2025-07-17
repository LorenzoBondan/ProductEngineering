package br.com.todeschini.itemservicepersistence.publico.cor;

import br.com.todeschini.itemservicepersistence.entities.Cor;
import br.com.todeschini.libauditpersistence.projections.AuditoriaProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CorRepository extends CrudRepository<Cor, Integer> {

    @Query(nativeQuery = true, value = """
        SELECT criadopor, criadoem, situacao FROM tb_cor WHERE cdcor = :id
    """)
    AuditoriaProjection findAuditoriaById(@Param("id") Integer id);
}
