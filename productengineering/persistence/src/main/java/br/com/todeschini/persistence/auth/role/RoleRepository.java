package br.com.todeschini.persistence.auth.role;

import br.com.todeschini.domain.metadata.QueryService;
import br.com.todeschini.persistence.entities.auth.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

@QueryService
public interface RoleRepository extends JpaRepository<Role, Long> {

    Collection<Role> findByAuthority(String authority); // usado para verificar registro duplicado

    <T> Page<T> findByAuthorityStartingWithIgnoreCase(String authority, Pageable pageable, Class<T> type);
}
