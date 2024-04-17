package br.com.todeschini.persistence.mdp.usededgebanding;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.mdp.UsedEdgeBanding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface UsedEdgeBandingRepository extends JpaRepository<UsedEdgeBanding, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_used_edge_banding ue WHERE ue.edge_banding_id = :edgeBandingId AND ue.son_id = :sonId
            """)
    Collection<UsedEdgeBanding> findByEdgeBandingIdAndMDPSonId(@Param("edgeBandingId") Long edgeBandingId, @Param("sonId") Long sonId); // usado para verificar registro duplicado

    @Query("SELECT c FROM UsedEdgeBanding c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<UsedEdgeBanding> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusIn(List<Status> statusList, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_used_edge_banding WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_used_edge_banding WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
