package br.com.todeschini.persistence.publico.medidas;

import br.com.todeschini.persistence.entities.publico.Medidas;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MedidasRepository extends CrudRepository<Medidas, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_medidas WHERE cdmedidas = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_medidas WHERE cdmedidas = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
