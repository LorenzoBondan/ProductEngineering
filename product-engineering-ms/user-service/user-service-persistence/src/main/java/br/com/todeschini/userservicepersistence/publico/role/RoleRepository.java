package br.com.todeschini.userservicepersistence.publico.role;

import br.com.todeschini.libvalidationhandler.metadata.QueryService;
import br.com.todeschini.userservicepersistence.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

@QueryService
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
