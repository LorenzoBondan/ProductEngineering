package br.com.todeschini.persistence.mdf.painting;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.mdf.Painting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface PaintingRepository extends JpaRepository<Painting, Long> {

    @Query("SELECT c FROM Painting c WHERE c.color.code = :colorId AND c.paintingType.id = :paintingTypeId")
    Collection<Painting> findByColorAndPaintingType(Long colorId, Long paintingTypeId); // usado para verificar registro duplicado

    @Query("SELECT c FROM Painting c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.code = :id)")
    List<Painting> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusInAndDescriptionContainingIgnoreCase(List<Status> statusList, String description, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_painting WHERE code = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_painting WHERE code = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
