package br.com.todeschini.persistence.trash;

import br.com.todeschini.persistence.entities.trash.Trash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TrashRepository extends JpaRepository<Trash, Long> {

    Boolean existsByEntityId(Map<String, Object> entityId);

    Trash findByEntityId(Map<String, Object> entityId);

    @Query(nativeQuery = true, value = """
        select * from trash t
        where LOWER(t.username) LIKE LOWER(CONCAT(:username, '%'))
        and t.date >= :startDate
        and t.date <= :endDate
        and LOWER(t.table_name) LIKE LOWER(CONCAT(:tableName, '%'))
        """)
    List<Trash> findByUserAndDateRangeAndTable(@Param("username") String username,
                                               @Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate,
                                               @Param("tableName") String tableName);

}
