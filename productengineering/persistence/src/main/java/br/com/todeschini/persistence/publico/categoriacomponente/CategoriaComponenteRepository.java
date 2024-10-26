package br.com.todeschini.persistence.publico.categoriacomponente;

import br.com.todeschini.persistence.entities.publico.CategoriaComponente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface CategoriaComponenteRepository extends CrudRepository<CategoriaComponente, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT criadopor FROM tb_categoria_componente WHERE cdcategoria_componente = :id
            """)
    String findCriadoporById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = """
            SELECT criadoem FROM tb_categoria_componente WHERE cdcategoria_componente = :id
            """)
    LocalDateTime findCriadoemById(@Param("id") Integer id);
}
