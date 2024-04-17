package br.com.todeschini.persistence.packaging.usedplastic;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.packaging.UsedPlastic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface UsedPlasticRepository extends JpaRepository<UsedPlastic, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_used_plastic uc WHERE uc.plastic_id = :plasticId AND uc.ghost_id LIKE :ghostId
            """)
    Collection<UsedPlastic> findByPlasticIdAndGhostId(@Param("plasticId") Long plasticId, @Param("ghostId") String ghostId); // usado para verificar registro duplicado

    @Query("SELECT c FROM UsedPlastic c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<UsedPlastic> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusIn(List<Status> statusList, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_used_plastic WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_used_plastic WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
