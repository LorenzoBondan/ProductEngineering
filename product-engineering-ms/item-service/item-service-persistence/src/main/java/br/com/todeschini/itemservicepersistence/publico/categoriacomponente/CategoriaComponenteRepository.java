package br.com.todeschini.itemservicepersistence.publico.categoriacomponente;

import br.com.todeschini.itemservicepersistence.entities.CategoriaComponente;
import br.com.todeschini.libauditpersistence.projections.AuditoriaProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CategoriaComponenteRepository extends CrudRepository<CategoriaComponente, Integer> {

    @Query(nativeQuery = true, value = """
        SELECT criadopor, criadoem, situacao FROM tb_categoria_componente WHERE cdcategoria_componente = :id
    """)
    AuditoriaProjection findAuditoriaById(@Param("id") Integer id);
}
