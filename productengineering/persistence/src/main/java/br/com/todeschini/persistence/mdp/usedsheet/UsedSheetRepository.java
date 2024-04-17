package br.com.todeschini.persistence.mdp.usedsheet;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.mdp.UsedSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface UsedSheetRepository extends JpaRepository<UsedSheet, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_used_sheet ue WHERE ue.sheet_id = :sheetId AND ue.son_id = :sonId
            """)
    Collection<UsedSheet> findBySheetIdAndMDPSonId(@Param("sheetId") Long sheetId, @Param("sonId") Long sonId); // usado para verificar registro duplicado

    @Query("SELECT c FROM UsedSheet c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<UsedSheet> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusIn(List<Status> statusList, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_used_sheet WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_used_sheet WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
