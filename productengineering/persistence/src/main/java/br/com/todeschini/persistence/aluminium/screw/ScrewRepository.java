package br.com.todeschini.persistence.aluminium.screw;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.aluminium.Screw;
import br.com.todeschini.persistence.entities.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface ScrewRepository extends JpaRepository<Screw, Long> {

    Collection<Screw> findByDescription(String description); // usado para verificar registro duplicado

    @Query("SELECT c FROM Screw c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.code = :id)")
    List<Screw> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusInAndDescriptionContainingIgnoreCase(List<Status> statusList, String description, Pageable pageable, Class<T> type);
}
