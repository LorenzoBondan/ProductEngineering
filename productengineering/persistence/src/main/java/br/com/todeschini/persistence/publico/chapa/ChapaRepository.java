package br.com.todeschini.persistence.publico.chapa;

import br.com.todeschini.persistence.entities.publico.Chapa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ChapaRepository extends CrudRepository<Chapa, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_material WHERE cdmaterial = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_material WHERE cdmaterial = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
