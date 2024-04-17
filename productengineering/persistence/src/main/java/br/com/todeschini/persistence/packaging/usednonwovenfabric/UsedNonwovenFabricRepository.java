package br.com.todeschini.persistence.packaging.usednonwovenfabric;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.packaging.UsedNonwovenFabric;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface UsedNonwovenFabricRepository extends JpaRepository<UsedNonwovenFabric, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_used_nonwoven_fabric uc WHERE uc.nonwoven_fabric_id = :nonwovenFabricId AND uc.ghost_id LIKE :ghostId
            """)
    Collection<UsedNonwovenFabric> findByNonwovenFabricIdAndGhostId(@Param("nonwovenFabricId") Long nonwovenFabricId, @Param("ghostId") String ghostId); // usado para verificar registro duplicado

    @Query("SELECT c FROM UsedNonwovenFabric c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<UsedNonwovenFabric> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusIn(List<Status> statusList, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_used_nonwoven_fabric WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_used_nonwoven_fabric WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
