package br.com.todeschini.persistence.mdf.paintingtype;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.mdf.PaintingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface PaintingTypeRepository extends JpaRepository<PaintingType, Long> {

    Collection<PaintingType> findByDescription(String description); // usado para verificar registro duplicado

    @Query("SELECT c FROM PaintingType c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<PaintingType> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusInAndDescriptionContainingIgnoreCase(List<Status> statusList, String description, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_painting_type WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_painting_type WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
