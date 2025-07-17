package br.com.todeschini.itemservicepersistence.publico.filho;

import br.com.todeschini.itemservicepersistence.entities.Filho;
import br.com.todeschini.libauditpersistence.projections.AuditoriaProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FilhoRepository extends CrudRepository<Filho, Integer> {

    @Query(nativeQuery = true, value = """
        SELECT criadopor, criadoem, situacao FROM tb_filho WHERE cdfilho = :id
    """)
    AuditoriaProjection findAuditoriaById(@Param("id") Integer id);
}
