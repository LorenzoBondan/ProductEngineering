package br.com.todeschini.persistence.publico.cor;

import br.com.todeschini.persistence.entities.publico.Cor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface CorRepository extends CrudRepository<Cor, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_cor WHERE cdcor = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_cor WHERE cdcor = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
