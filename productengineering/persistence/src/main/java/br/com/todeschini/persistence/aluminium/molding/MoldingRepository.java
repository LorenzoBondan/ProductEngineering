package br.com.todeschini.persistence.aluminium.molding;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.aluminium.Molding;
import br.com.todeschini.persistence.entities.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@QueryService
public interface MoldingRepository extends JpaRepository<Molding, Long> {

    Collection<Molding> findByDescription(String description); // usado para verificar registro duplicado

    @Query("SELECT c FROM Molding c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.code = :id)")
    List<Molding> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusInAndDescriptionContainingIgnoreCase(List<Status> statusList, String description, Pageable pageable, Class<T> type);
}
