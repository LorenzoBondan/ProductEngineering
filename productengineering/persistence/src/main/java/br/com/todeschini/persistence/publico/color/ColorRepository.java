package br.com.todeschini.persistence.publico.color;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.publico.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@QueryService
public interface ColorRepository extends JpaRepository<Color, Long> {

    Collection<Color> findByName(String name); // usado para verificar registro duplicado

    @Query("SELECT c FROM Color c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.code = :id)")
    List<Color> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusInAndNameContainingIgnoreCase(List<Status> statusList, String name, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = """
            SELECT created_by FROM tb_color WHERE code = :id
            """)
    String findCreatedBy(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT creation_date FROM tb_color WHERE code = :id
            """)
    LocalDateTime findCreationDate(@Param("id") Long id);
}
