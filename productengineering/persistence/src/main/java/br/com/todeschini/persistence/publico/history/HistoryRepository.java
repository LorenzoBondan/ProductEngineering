package br.com.todeschini.persistence.publico.history;

import br.com.todeschini.persistence.entities.publico.History;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM t_history WHERE tabname = :tabname AND old_val->>:idName = :recordId")
    List<History> findByTabnameAndRecordId(@Param("tabname") String tabname,
                                           @Param("idName") String idName,
                                           @Param("recordId") String recordId);
}
