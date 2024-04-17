package br.com.todeschini.persistence.aluminium.glass;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.aluminium.Glass;
import br.com.todeschini.persistence.entities.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface GlassRepository extends JpaRepository<Glass, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_item i WHERE i.color_id = :colorId
            AND i.measure1 = (:measure1 - 5)
            AND i.measure2 = (:measure2 - 5)
            AND i.dtype = 'Glass'
            """)
    Glass findGlassByColorIdAndMeasures(@Param("colorId") Long colorId,
                                        @Param("measure1") Integer measure1,
                                        @Param("measure2") Integer measure2);

    Collection<Glass> findByDescription(String description); // usado para verificar registro duplicado

    @Query("SELECT c FROM Glass c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.code = :id)")
    List<Glass> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusInAndDescriptionContainingIgnoreCase(List<Status> statusList, String description, Pageable pageable, Class<T> type);
}
