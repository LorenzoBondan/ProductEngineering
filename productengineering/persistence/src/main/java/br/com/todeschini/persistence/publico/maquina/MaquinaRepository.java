package br.com.todeschini.persistence.publico.maquina;

import br.com.todeschini.persistence.entities.publico.Maquina;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MaquinaRepository extends CrudRepository<Maquina, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_maquina WHERE cdmaquina = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_maquina WHERE cdmaquina = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
