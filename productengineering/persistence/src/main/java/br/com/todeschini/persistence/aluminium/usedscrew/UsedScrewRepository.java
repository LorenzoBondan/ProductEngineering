package br.com.todeschini.persistence.aluminium.usedscrew;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.aluminium.UsedScrew;
import br.com.todeschini.persistence.entities.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface UsedScrewRepository extends JpaRepository<UsedScrew, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_used_screw ue WHERE ue.Screw_id = :screwId AND ue.aluminium_son_id = :sonId
            """)
    Collection<UsedScrew> findByScrewIdAndAluminiumSonId(@Param("screwId") Long screwId, @Param("sonId") Long sonId); // usado para verificar registro duplicado

    @Query("SELECT c FROM UsedScrew c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<UsedScrew> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusIn(List<Status> statusList, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_used_screw WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_used_screw WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
