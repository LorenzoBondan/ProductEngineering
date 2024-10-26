package br.com.todeschini.persistence.publico.roteiro;

import br.com.todeschini.persistence.entities.publico.Roteiro;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface RoteiroRepository extends CrudRepository<Roteiro, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_roteiro WHERE cdroteiro = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_roteiro WHERE cdroteiro = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
