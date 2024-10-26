package br.com.todeschini.persistence.publico.filho;

import br.com.todeschini.persistence.entities.publico.Filho;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface FilhoRepository extends CrudRepository<Filho, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_filho WHERE cdfilho = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_filho WHERE cdfilho = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
