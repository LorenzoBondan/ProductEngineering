package br.com.todeschini.persistence.packaging.ghost;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.packaging.Ghost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface GhostRepository extends JpaRepository<Ghost, Long> {

    Ghost findByCode(String code);

    Boolean existsByCode(String code);

    void deleteByCode(String code);

    Collection<Ghost> findByDescription(String description); // usado para verificar registro duplicado

    @Query("SELECT c FROM Ghost c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.code LIKE :id)")
    List<Ghost> findAllActiveAndCurrentOne(@Param("id") String id);

    <T> Page<T> findByStatusInAndDescriptionContainingIgnoreCase(List<Status> statusList, String description, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_ghost WHERE code LIKE :id
            """)
    String findCreatedBy(@Param("id") String id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_ghost WHERE code LIKE :id
            """)
    LocalDateTime findCreationDate(@Param("id") String id);
}
