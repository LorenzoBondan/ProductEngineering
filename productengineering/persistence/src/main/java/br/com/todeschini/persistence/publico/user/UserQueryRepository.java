package br.com.todeschini.persistence.publico.user;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.publico.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@QueryService
public interface UserQueryRepository extends PagingAndSortingRepository<User, Integer>, JpaSpecificationExecutor<User> {

    @Query(nativeQuery = true, value = "SELECT * FROM tb_user p WHERE p.situacao = 'ATIVO' OR (:id IS NOT NULL AND p.id = :id)")
    List<User> findAllActiveAndCurrentOne(@Param("id") Integer id);
}
