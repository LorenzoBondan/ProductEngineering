package br.com.todeschini.persistence.mdp.sheet;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.mdp.Sheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface SheetRepository extends JpaRepository<Sheet, Long> {

    @Query(nativeQuery = true, value = """
            SELECT s.* FROM tb_sheet s WHERE s.color_id = :colorId
            AND s.thickness = :thickness
            """)
    Sheet getByColorIdAndThickness(@Param("colorId") Long colorId, @Param("thickness") Integer thickness);

    @Query(nativeQuery = true, value = """
            SELECT s.* FROM tb_sheet s
            INNER JOIN tb_material m ON m.id = s.material_id
            WHERE s.thickness = :thickness AND s.faces = :faces
            AND m.name LIKE 'MDF'
            """)
    Sheet findByThicknessAndFaces(@Param("thickness") Double thickness, @Param("faces") Integer faces);

    Collection<Sheet> findByDescription(String description); // usado para verificar registro duplicado

    @Query("SELECT c FROM Sheet c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.code = :id)")
    List<Sheet> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusInAndDescriptionContainingIgnoreCase(List<Status> statusList, String description, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_sheet WHERE code = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_sheet WHERE code = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
