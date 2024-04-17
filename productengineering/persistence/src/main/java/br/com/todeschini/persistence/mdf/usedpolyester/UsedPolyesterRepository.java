package br.com.todeschini.persistence.mdf.usedpolyester;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.mdf.UsedPolyester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface UsedPolyesterRepository extends JpaRepository<UsedPolyester, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_used_polyester ue WHERE ue.polyester_id = :polyesterId AND ue.painting_son_id = :sonId
            """)
    Collection<UsedPolyester> findByPolyesterIdAndPaintingSonId(@Param("polyesterId") Long polyesterId, @Param("sonId") Long sonId); // usado para verificar registro duplicado

    @Query("SELECT c FROM UsedPolyester c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<UsedPolyester> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusIn(List<Status> statusList, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_used_polyester WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_used_polyester WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
