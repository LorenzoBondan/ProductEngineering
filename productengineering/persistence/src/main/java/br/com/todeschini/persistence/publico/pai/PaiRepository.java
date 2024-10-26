package br.com.todeschini.persistence.publico.pai;

import br.com.todeschini.persistence.entities.publico.Pai;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PaiRepository extends CrudRepository<Pai, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_pai WHERE cdpai = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_pai WHERE cdpai = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
