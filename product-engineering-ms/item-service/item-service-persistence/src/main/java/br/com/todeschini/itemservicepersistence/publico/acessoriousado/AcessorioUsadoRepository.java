package br.com.todeschini.itemservicepersistence.publico.acessoriousado;

import br.com.todeschini.itemservicepersistence.entities.AcessorioUsado;
import br.com.todeschini.libauditpersistence.projections.AuditoriaProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AcessorioUsadoRepository extends CrudRepository<AcessorioUsado, Integer> {

    @Query(nativeQuery = true, value = """
        SELECT criadopor, criadoem, situacao FROM tb_acessorio_usado WHERE cdacessorio_usado = :id
    """)
    AuditoriaProjection findAuditoriaById(@Param("id") Integer id);
}
