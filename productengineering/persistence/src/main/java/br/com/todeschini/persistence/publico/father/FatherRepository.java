package br.com.todeschini.persistence.publico.father;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.entities.publico.Father;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@QueryService
@Repository
public interface FatherRepository extends JpaRepository<Father, Long> {

    Collection<Father> findByDescription(String description); // usado para verificar registro duplicado

    @Query("SELECT c FROM Father c WHERE c.status = 'ACTIVE' OR (:id IS NOT NULL AND c.code = :id)")
    List<Father> findAllActiveAndCurrentOne(@Param("id") Long id);

    <T> Page<T> findByStatusInAndDescriptionContainingIgnoreCase(List<Status> statusList, String description, Pageable pageable, Class<T> type);

    @Query(nativeQuery = true, value = "SELECT f.* FROM tb_item f WHERE f.code = :code AND f.dtype LIKE 'Father'")
    Optional<Father> findByCode(@Param("code") Long code);
}
