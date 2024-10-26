package br.com.todeschini.persistence.publico.roteiromaquina;

import br.com.todeschini.persistence.entities.publico.RoteiroMaquina;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface RoteiroMaquinaRepository extends CrudRepository<RoteiroMaquina, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_roteiro_maquina WHERE cdroteiro_maquina = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_roteiro_maquina WHERE cdroteiro_maquina = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
