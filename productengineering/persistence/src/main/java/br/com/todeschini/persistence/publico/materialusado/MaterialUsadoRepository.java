package br.com.todeschini.persistence.publico.materialusado;

import br.com.todeschini.persistence.entities.publico.MaterialUsado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MaterialUsadoRepository extends CrudRepository<MaterialUsado, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_material_usado WHERE cdmaterial_usado = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_material_usado WHERE cdmaterial_usado = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
