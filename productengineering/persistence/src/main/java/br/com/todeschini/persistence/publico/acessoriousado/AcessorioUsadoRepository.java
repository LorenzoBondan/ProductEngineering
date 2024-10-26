package br.com.todeschini.persistence.publico.acessoriousado;

import br.com.todeschini.persistence.entities.publico.AcessorioUsado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AcessorioUsadoRepository extends CrudRepository<AcessorioUsado, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_acessorio_usado WHERE cdacessorio_usado = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_acessorio_usado WHERE cdacessorio_usado = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
