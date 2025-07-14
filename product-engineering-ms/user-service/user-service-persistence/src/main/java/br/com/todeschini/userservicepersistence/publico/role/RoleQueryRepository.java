package br.com.todeschini.userservicepersistence.publico.role;

import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import br.com.todeschini.userservicepersistence.entities.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

@QueryService
public interface RoleQueryRepository extends PagingAndSortingRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
}