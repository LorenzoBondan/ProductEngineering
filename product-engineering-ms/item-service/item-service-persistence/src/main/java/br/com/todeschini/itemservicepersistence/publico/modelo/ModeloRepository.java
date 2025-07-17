package br.com.todeschini.itemservicepersistence.publico.modelo;

import br.com.todeschini.itemservicepersistence.entities.Modelo;
import br.com.todeschini.libauditpersistence.projections.AuditoriaProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ModeloRepository extends CrudRepository<Modelo, Integer> {

    @Query(nativeQuery = true, value = """
        SELECT criadopor, criadoem, situacao FROM tb_modelo WHERE cdmodelo = :id
    """)
    AuditoriaProjection findAuditoriaById(@Param("id") Integer id);
}
