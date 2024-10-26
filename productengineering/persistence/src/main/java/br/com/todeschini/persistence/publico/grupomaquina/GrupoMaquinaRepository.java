package br.com.todeschini.persistence.publico.grupomaquina;

import br.com.todeschini.persistence.entities.publico.GrupoMaquina;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface GrupoMaquinaRepository extends CrudRepository<GrupoMaquina, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_grupo_maquina WHERE cdgrupo_maquina = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_grupo_maquina WHERE cdgrupo_maquina = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
