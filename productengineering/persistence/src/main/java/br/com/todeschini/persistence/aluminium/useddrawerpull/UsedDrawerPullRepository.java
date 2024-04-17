package br.com.todeschini.persistence.aluminium.useddrawerpull;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.aluminium.UsedDrawerPull;
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
public interface UsedDrawerPullRepository extends JpaRepository<UsedDrawerPull, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_used_drawer_pull ue WHERE ue.drawer_pull_id = :drawerPullId AND ue.aluminium_son_id = :sonId
            """)
    Collection<UsedDrawerPull> findByDrawerPullIdAndAluminiumSonId(@Param("drawerPullId") Long drawerPullId, @Param("sonId") Long sonId); // usado para verificar registro duplicado

    @Query("SELECT c FROM UsedDrawerPull c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<UsedDrawerPull> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusIn(List<Status> statusList, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_used_drawer_pull WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_used_drawer_pull WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
