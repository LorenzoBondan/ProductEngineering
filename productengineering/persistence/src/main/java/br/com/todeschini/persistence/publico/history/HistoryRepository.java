package br.com.todeschini.persistence.publico.history;

import br.com.todeschini.persistence.entities.publico.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM t_history WHERE tabname = :tabname AND old_val->>:idName = :recordId")
    List<History> findByTabnameAndRecordId(@Param("tabname") String tabname,
                                           @Param("idName") String idName,
                                           @Param("recordId") String recordId);

    @Query(nativeQuery = true, value = """
        SELECT * FROM public.t_history t
        WHERE upper(t.tabname) LIKE upper(concat(:tabname, '%'))
        AND (
            CASE
                WHEN :recordId ~ '^[0-9]+$' THEN CAST(t.old_val->>:idName AS INTEGER) = CAST(:recordId AS INTEGER)  -- Se :recordId for número
                ELSE unaccent(lower(t.old_val->>:idName)) LIKE unaccent(lower(concat(:recordId, '%')))  -- Se :recordId for string, ignorando case e acentuação
            END
        )
        AND t.tstamp >= :dataInicial
        AND t.tstamp <= :dataFinal
        AND upper(t.operation) LIKE upper(concat(:operation, '%'))
    """)
    Page<History> findByTabnameAndTstampBetweenAndOperationAndRecordId(
            @Param("tabname") String tabname,
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("dataFinal") LocalDateTime dataFinal,
            @Param("operation") String operation,
            @Param("idName") String idName,
            @Param("recordId") String recordId,
            Pageable pageable);
}
