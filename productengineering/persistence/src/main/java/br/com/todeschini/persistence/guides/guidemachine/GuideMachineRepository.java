package br.com.todeschini.persistence.guides.guidemachine;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.guides.GuideMachine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface GuideMachineRepository extends JpaRepository<GuideMachine, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_guide_machine gm WHERE gm.guide_id = :guideId AND gm.machine_id = :machineId
            """)
    Collection<GuideMachine> findByGuideIdAndMachineId(@Param("guideId") Long guideId, @Param("machineId") Long machineId);

    @Query("SELECT c FROM Guide c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<GuideMachine> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusIn(List<Status> statusList, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_guide_machine WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_guide_machine WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
