package br.com.todeschini.persistence.publico.cantoneira;

import br.com.todeschini.persistence.entities.publico.Cantoneira;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface CantoneiraRepository extends CrudRepository<Cantoneira, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_material WHERE cdmaterial = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_material WHERE cdmaterial = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
