package br.com.todeschini.persistence.publico.lixeira;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.Lixeira;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@QueryService
public interface LixeiraRepository extends CrudRepository<Lixeira, Integer> {

    Boolean existsByEntidadeid(Map<String, Object> entidadeId);

    Lixeira findByEntidadeid(Map<String, Object> entidadeId);

    @Query(nativeQuery = true, value = """
        select * from tb_lixeira l
        where LOWER(UNACCENT(l.usuario)) LIKE LOWER(UNACCENT(CONCAT(:usuario, '%')))
        and l.data >= :dataInicio
        and l.data <= :dataFim
        and LOWER(UNACCENT(l.nometabela)) LIKE LOWER(UNACCENT(CONCAT(:tabela, '%')))
        """)
    List<Lixeira> findByUsuarioAndDataRangeAndTabela(@Param("usuario") String usuario,
                                                     @Param("dataInicio") LocalDateTime dataInicio,
                                                     @Param("dataFim") LocalDateTime dataFim,
                                                     @Param("tabela") String tabela);

}
