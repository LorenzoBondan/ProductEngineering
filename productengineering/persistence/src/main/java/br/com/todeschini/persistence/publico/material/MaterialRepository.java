package br.com.todeschini.persistence.publico.material;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.publico.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface MaterialRepository extends JpaRepository<Material, Long> {

    Collection<Material> findByName(String name); // usado para verificar registro duplicado

    @Query("SELECT c FROM Material c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.id = :id)")
    List<Material> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusInAndNameContainingIgnoreCase(List<Status> statusList, String name, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_material WHERE id = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_material WHERE id = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
