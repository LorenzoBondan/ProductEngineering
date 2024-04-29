package br.com.todeschini.persistence.guides.machine;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.guides.Machine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface MachineRepository extends JpaRepository<Machine, Long> {

    Collection<Machine> findByName(String name); // usado para verificar registro duplicado

    @Query("SELECT c FROM Machine c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<Machine> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusInAndNameContainingIgnoreCase(List<Status> statusList, String name, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_machine WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_machine WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
