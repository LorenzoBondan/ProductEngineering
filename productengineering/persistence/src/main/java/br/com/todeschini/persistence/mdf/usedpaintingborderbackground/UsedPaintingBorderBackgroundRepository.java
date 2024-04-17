package br.com.todeschini.persistence.mdf.usedpaintingborderbackground;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.mdf.UsedPaintingBorderBackground;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface UsedPaintingBorderBackgroundRepository extends JpaRepository<UsedPaintingBorderBackground, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_used_painting_border_background ue WHERE ue.painting_border_background_id = :paintingBorderBackgroundId AND ue.painting_son_id = :sonId
            """)
    Collection<UsedPaintingBorderBackground> findByPaintingBorderBackgroundIdAndMDPSonId(@Param("paintingBorderBackgroundId") Long paintingBorderBackgroundId, @Param("sonId") Long sonId); // usado para verificar registro duplicado

    @Query("SELECT c FROM UsedPaintingBorderBackground c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<UsedPaintingBorderBackground> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusIn(List<Status> statusList, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_used_painting_border_background WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_used_painting_border_background WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
