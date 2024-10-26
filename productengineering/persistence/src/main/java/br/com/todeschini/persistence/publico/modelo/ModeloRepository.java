package br.com.todeschini.persistence.publico.modelo;

import br.com.todeschini.persistence.entities.publico.Modelo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ModeloRepository extends CrudRepository<Modelo, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_modelo WHERE cdmodelo = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_modelo WHERE cdmodelo = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
