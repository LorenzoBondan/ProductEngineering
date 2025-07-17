package br.com.todeschini.itemservicepersistence.publico.acessorio;

import br.com.todeschini.itemservicepersistence.entities.Acessorio;
import br.com.todeschini.libauditpersistence.projections.AuditoriaProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AcessorioRepository extends CrudRepository<Acessorio, Integer> {

    @Query(nativeQuery = true, value = """
        SELECT criadopor, criadoem, situacao FROM tb_acessorio WHERE cdacessorio = :id
    """)
    AuditoriaProjection findAuditoriaById(@Param("id") Integer id);
}
