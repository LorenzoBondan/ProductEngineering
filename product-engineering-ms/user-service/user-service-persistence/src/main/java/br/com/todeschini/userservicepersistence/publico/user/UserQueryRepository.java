package br.com.todeschini.userservicepersistence.publico.user;

import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import br.com.todeschini.userservicepersistence.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

@QueryService
public interface UserQueryRepository extends PagingAndSortingRepository<User, Integer>, JpaSpecificationExecutor<User> {
}
