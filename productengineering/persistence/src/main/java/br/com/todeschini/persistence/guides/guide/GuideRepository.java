package br.com.todeschini.persistence.guides.guide;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.guides.Guide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface GuideRepository extends JpaRepository<Guide, Long> {

    @Query(nativeQuery = true, value = """
        SELECT * FROM tb_guide g
        WHERE
            EXISTS (SELECT 1 FROM tb_item s WHERE LEFT(CAST(s.code AS VARCHAR), 6) = :code AND s.guide_id = g.id)
            OR EXISTS (SELECT 1 FROM tb_item ps WHERE LEFT(CAST(ps.code AS VARCHAR), 6) = :code AND ps.guide_id = g.id)
            OR EXISTS (SELECT 1 FROM tb_item als WHERE LEFT(CAST(als.code AS VARCHAR), 6) = :code AND als.guide_id = g.id)
        """)
    Guide findGuideBySixDigitCode(@Param("code") String code);

    @Query("SELECT c FROM Guide c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<Guide> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusIn(List<Status> statusList, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_guide WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_guide WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
