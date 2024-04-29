package br.com.todeschini.persistence.audit;

import br.com.todeschini.persistence.entities.audit.T_History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface THistoryRepository extends JpaRepository<T_History, Long> {

    @Query(nativeQuery = true, value = """
        SELECT * FROM t_history
        WHERE LOWER(tabname) LIKE LOWER(CONCAT(:tabname, '%'))
        AND LOWER(operation) LIKE LOWER(CONCAT(:operation, '%'))
        AND tstamp >= :startDate
        AND tstamp <= :endDate
        AND (
            CASE
                WHEN :idName IS NULL OR :idName = '' THEN TRUE
                ELSE LOWER(old_val->>:idName) LIKE LOWER(CONCAT(:idValue, '%'))
            END
        )
    """)
    List<T_History> findByTabnameAndOperationAndIdNameAndIdValueAndDateRange(
            @Param("tabname") String tabname,
            @Param("operation") String operation,
            @Param("idName") String idName,
            @Param("idValue") String idValue,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
