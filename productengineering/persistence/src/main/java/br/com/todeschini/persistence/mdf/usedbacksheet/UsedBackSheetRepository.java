package br.com.todeschini.persistence.mdf.usedbacksheet;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.mdf.UsedBackSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface UsedBackSheetRepository extends JpaRepository<UsedBackSheet, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_used_back_sheet ue WHERE ue.back_id = :backId AND ue.sheet_id = :sheetId
            """)
    Collection<UsedBackSheet> findByBackIdAndSheetId(@Param("backId") Long backId, @Param("sheetId") Long sheetId); // usado para verificar registro duplicado

    @Query("SELECT c FROM UsedBackSheet c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<UsedBackSheet> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusIn(List<Status> statusList, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_used_back_sheet WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_used_back_sheet WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
