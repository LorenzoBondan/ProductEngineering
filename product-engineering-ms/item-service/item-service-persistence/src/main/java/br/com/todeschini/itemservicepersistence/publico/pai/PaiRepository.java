package br.com.todeschini.itemservicepersistence.publico.pai;

import br.com.todeschini.itemservicepersistence.entities.Pai;
import br.com.todeschini.libauditpersistence.projections.AuditoriaProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PaiRepository extends CrudRepository<Pai, Integer> {

    @Query(nativeQuery = true, value = """
        SELECT criadopor, criadoem, situacao FROM tb_pai WHERE cdpai = :id
    """)
    AuditoriaProjection findAuditoriaById(@Param("id") Integer id);
}
