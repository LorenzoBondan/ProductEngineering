package br.com.todeschini.persistence.publico.acessorio;

import br.com.todeschini.persistence.entities.publico.Acessorio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AcessorioRepository extends CrudRepository<Acessorio, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_acessorio WHERE cdacessorio = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_acessorio WHERE cdacessorio = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
