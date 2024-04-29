package br.com.todeschini.persistence.guides.machinegroup;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.guides.MachineGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface MachineGroupRepository extends JpaRepository<MachineGroup, Long> {

    Collection<MachineGroup> findByName(String name); // usado para verificar registro duplicado

    @Query("SELECT c FROM MachineGroup c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<MachineGroup> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusInAndNameContainingIgnoreCase(List<Status> statusList, String name, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_machine_group WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_machine_group WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
